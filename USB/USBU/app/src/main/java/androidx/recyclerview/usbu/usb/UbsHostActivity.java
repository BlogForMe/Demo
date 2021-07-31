package androidx.recyclerview.usbu.usb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.usbu.R;
import androidx.recyclerview.usbu.receiver.USBBroadcastReceiver;

import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.os.Bundle;


public class UbsHostActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ubs_host);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubs_host);

        ///< @a 这种不好使
        //        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_MEDIA_MOUNTED);//插
        //        intentFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);//拔
        //        intentFilter.addAction(Intent.ACTION_MEDIA_REMOVED);  //完全拔出
        //        intentFilter.addDataScheme("file");//没有这行监听不起作用
        //        registerReceiver(mSdcardReceiver, intentFilter,
        //                "android.permission.READ_EXTERNAL_STORAGE",null);

        ///< 监听otg插入 拔出
        IntentFilter usbDeviceStateFilter = new IntentFilter();
        usbDeviceStateFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        usbDeviceStateFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(usbBroadcastReceiver, usbDeviceStateFilter);
        //注册监听自定义广播
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(usbBroadcastReceiver, filter);
    }
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    private USBBroadcastReceiver usbBroadcastReceiver = new USBBroadcastReceiver();

    ///< @a 这种不好使
    //    private BroadcastReceiver mSdcardReceiver = new BroadcastReceiver() {
    //        @Override
    //        public void onReceive(Context context, Intent intent) {
    //            if (intent.getAction().equals(Intent.ACTION_MEDIA_UNMOUNTED)) {
    //                Toast.makeText(context, "插入", Toast.LENGTH_SHORT).show();
    //            } else if (intent.getAction().equals(Intent.ACTION_MEDIA_REMOVED)) {
    //                Toast.makeText(context, "remove ACTION_MEDIA_REMOVED", Toast.LENGTH_SHORT).show();
    //            }
    //        }
    //    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(usbBroadcastReceiver);
    }
}

