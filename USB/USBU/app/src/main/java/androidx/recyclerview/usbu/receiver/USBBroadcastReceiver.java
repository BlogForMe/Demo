package androidx.recyclerview.usbu.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/24.
 */

public class USBBroadcastReceiver extends BroadcastReceiver {
    public final static String ACTION_USB_PERMISSION = "android.usb.permission.action";

    private List<USBReceiverEvent> list = new ArrayList<>();
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case ACTION_USB_PERMISSION://接受到自定义广播
                UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                //允许权限申请
                if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                    if (usbDevice != null) {
                        //用户已授权，可以进行读取操作
//                        dispathEvent(context);
                        for (USBReceiverEvent usbReceiverEvent:list){
                            usbReceiverEvent.getUsbDevice(usbDevice);
                        }
                    } else {
                        Toast.makeText(context,"没有插入U盘",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context,"未获取到U盘权限",Toast.LENGTH_SHORT).show();
                }
                break;

            case UsbManager.ACTION_USB_DEVICE_ATTACHED://接收到存储设备插入广播
                UsbDevice device_add = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                if (device_add != null) {
                    for (USBReceiverEvent usbReceiverEvent:list){
                        usbReceiverEvent.getUsbDevice(device_add);
                    }
                }

                break;
            case UsbManager.ACTION_USB_DEVICE_DETACHED://接收到存储设备拔出广播
                for (USBReceiverEvent usbReceiverEvent:list){
                    usbReceiverEvent.removeUsbDevice();
                }
                break;
        }
    }

    private void test(Context context,UsbDevice device){
        //设备管理器
        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        //获取U盘存储设备
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
            //读取设备是否有权限
            if (usbManager.hasPermission(device)) {

            } else {
                //没有权限，进行申请
                usbManager.requestPermission(device, pendingIntent);
            }
    }

    private void dispathEvent(UsbDevice device ){
        for (USBReceiverEvent usbReceiverEvent:list){
            usbReceiverEvent.getUsbDevice(device);
        }
    }

    public void addUSBReceiverEvent(USBReceiverEvent usbReceiverEvent){
        this.list.add(usbReceiverEvent);
    }

    public void removeUSBReceiverEvent(USBReceiverEvent usbReceiverEvent){
        list.remove(usbReceiverEvent);
    }

    public interface USBReceiverEvent{
        void getUsbDevice(UsbDevice usbDevice);
        void removeUsbDevice();
    }

}
