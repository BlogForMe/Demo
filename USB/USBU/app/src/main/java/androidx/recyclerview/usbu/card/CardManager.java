package androidx.recyclerview.usbu.card;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.recyclerview.usbu.receiver.USBBroadcastReceiver;

import com.Routon.iDRHIDLib.iDRHIDDev;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static android.text.TextUtils.isEmpty;
import static androidx.recyclerview.usbu.receiver.USBBroadcastReceiver.ACTION_USB_PERMISSION;

/**
 * Created by Administrator on 2018/7/24.
 * https://zhuanlan.zhihu.com/p/44973627
 */

public class CardManager implements CardInterface, USBBroadcastReceiver.USBReceiverEvent {

    private List<CardReadInterface> list = new ArrayList<>();

    private UsbManager mUsbManager;
    private UsbDevice mDevice;
    private iDRHIDDev mHIDDev;
    private Context mContext;

    private boolean mCheckTimeOut = true;

    public iDRHIDDev.SecondIDInfo getSecondIDInfo() {
        return mSecondIDInfo;
    }

    private iDRHIDDev.SecondIDInfo mSecondIDInfo;
    private USBBroadcastReceiver mUsbReceiver;

    @Override
    public void init(Context context) {
        if (mContext != null) return;

        this.mContext = context;
        mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);

        mDevice = null;

        mHIDDev = new iDRHIDDev();

        mUsbReceiver = new USBBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        mContext.registerReceiver(mUsbReceiver, filter);

        Log.i("Alan", mUsbManager.getDeviceList().size() + ":size");
        for (UsbDevice device : mUsbManager.getDeviceList().values()) {
            if (device.getVendorId() == 1061 && device.getProductId() == 33113) {
                if (!mUsbManager.hasPermission(device)) {
                    Intent intent = new Intent(ACTION_USB_PERMISSION);
                    PendingIntent mPermissionIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
                    mUsbManager.requestPermission(device, mPermissionIntent);
                } else {
                    int ret = mHIDDev.openDevice(mUsbManager, device);

                    readCardInfo();
                    if (ret == 0) {
                        mDevice = device;
                    } else {
                        mDevice = null;
                    }
                }
            }
        }

        mUsbReceiver.addUSBReceiverEvent(this);
//        readCardInfo();
    }

    @Override
    public void openDevice() {
//        mHIDDev.openDevice()
    }

    @Override
    public void colseDevice() {
        mHIDDev.closeDevice();
    }

    Object object = new Object();
   private   String lastId =null;
    @Override
    public void readCardInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mHIDDev.isConnected()) {

                    int ret = mHIDDev.GetSamStaus();

                    if (ret >= 0) {
                        iDRHIDDev.SamIDInfo samIDInfo = mHIDDev.new SamIDInfo();

                        ret = mHIDDev.GetSamId(samIDInfo);
                        ret = mHIDDev.Authenticate();
                        if (ret >= 0) {
                            iDRHIDDev.SecondIDInfo secondIDInfo = mHIDDev.new SecondIDInfo();
                            byte[] fingerPrint = new byte[1024];

                            ret = mHIDDev.ReadBaseFPMsg(secondIDInfo, fingerPrint);

                            if (ret >= 0 && (mSecondIDInfo == null || !mSecondIDInfo.id.equals(secondIDInfo.id))) {
                                mSecondIDInfo = secondIDInfo;
                                ret = mHIDDev.BeepLed(true, true, 500);
                                synchronized (object) {
                                    if (mSecondIDInfo.id!=null&&(!mSecondIDInfo.id.equals(lastId))) {
                                        Timber.i("mSecondIDInfo.id  " + mSecondIDInfo.id + "  lastId " + lastId);
                                        lastId = mSecondIDInfo.id;
                                        dispathCardInfo(0,0);
                                    }
                                }
                            }
                        }

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public void addVrCardInfo(String name, String id, int inputSex,int inputAge) {
            mSecondIDInfo = mHIDDev.new SecondIDInfo();
            mSecondIDInfo.id = id;
            mSecondIDInfo.name = name;
            if (!isEmpty(id)){
                mSecondIDInfo.gender = execute(id);
            }
            if (id!=null&&id.length() > 13)
                mSecondIDInfo.birthday = id.substring(6, 14);

        dispathCardInfo(inputSex,inputAge);
    }

    private void dispathCardInfo(int inputSex,int inputAge) {
        for (CardReadInterface cardReadInterface : list) {
            cardReadInterface.readCardInfo(mSecondIDInfo,inputSex,inputAge);
        }
        handler.removeMessages(0);
//        handler.sendEmptyMessageDelayed(0, 60 * 1000 * 30); //身份证超时
        mCheckTimeOut = true;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mSecondIDInfo = null;
            mCheckTimeOut = false;
            lastId = null;
            for (CardReadInterface cardReadInterface : list) {
                cardReadInterface.cardTimeOut();
            }
        }
    };

    public void clearData() {
        handler.sendEmptyMessage(0);
    }

    public String getCardId() {
        if (!mCheckTimeOut || mSecondIDInfo == null || mSecondIDInfo.id == null) return null;
        return mSecondIDInfo.id;
    }

    public String execute(String value) {
        if (value.length() < 17) return "";
        String sex = value.substring(16, 17);
        if (Integer.parseInt(sex) % 2 == 0) {
            sex = "女";
        } else {
            sex = "男";
        }
        return sex;
    }

    @Override
    public void getUsbDevice(UsbDevice usbDevice) {
        if (mContext == null) return;
        //设备管理器
        UsbManager usbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
        //获取U盘存储设备
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, new Intent(ACTION_USB_PERMISSION), 0);
        //读取设备是否有权限
        if (usbManager.hasPermission(usbDevice)) {
            int ret = mHIDDev.openDevice(mUsbManager, usbDevice);

            readCardInfo();
            if (ret == 0) {
                mDevice = usbDevice;
            } else {
                mDevice = null;
            }
        } else {
            //没有权限，进行申请
            usbManager.requestPermission(usbDevice, pendingIntent);
        }
    }

    @Override
    public void removeUsbDevice() {
        if (mDevice != null) {
            mHIDDev.closeDevice();
            mDevice = null;
        }
    }


    public void addCardReadInterface(CardReadInterface cardReadInterface) {
        this.list.add(cardReadInterface);
    }

    public void removeCardReadInterface(CardReadInterface cardReadInterface) {
        list.remove(cardReadInterface);
    }

    public interface CardReadInterface {
        void readCardInfo(iDRHIDDev.SecondIDInfo secondIDInfo, int inputSex, int inputAge);

        void cardTimeOut();
    }

    public void destroy(){
        mContext.unregisterReceiver(mUsbReceiver);
    }
}