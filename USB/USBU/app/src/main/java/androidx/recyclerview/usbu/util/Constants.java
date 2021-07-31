package com.casanube.medical.util;



import timber.log.Timber;


/**
 * Created by Administrator on 6/22/2017.
 */

public class Constants {

    public final static String ACTION_USB_PERMISSION = "android.usb.permission.action";
    /**
     * 1. 联系医生-收听消息-换人使用-查看帮助-切换模式
     * 2. 联系医生-体征测量-收听消息-换人使用-切换模式
     * 3. 联系助理-体征测量-收听消息-换人使用-切换模式
     */

    // 按键
    public final static int KEY_MESSAGE = 0X21; //收听消息
    public final static int KEY_HELP = 0X22; //查看帮助
    public final static int KEY_CONDOC = 0X23; //联系医生
    public final static int KEY_SWITCH = 0X24;  //换人使用
    public final static int KEY_INFO = 0X25;  //切换模式
    public final static int KEY_MEASURE_BLE = 0X26;  //体征测量


    // 智能家居设备
    public static final int DEVICE_TYPE_DOOR = 0X51;  //门磁
    public static final int DEVICE_TYPE_IR = 0X52;  //被动红外
    public static final int DEVICE_TYPE_AALARM = 0X53; //一建报警
    public static final int DEVICE_TYPE_SMOKE = 0X54; //烟雾探测
    public static final int DEVICE_TYPE_GAS = 0X55;  //可燃气体
    public static final int DEVICE_TYPE_WATER = 0X56; //水浸
    public static final int DEVICE_TYPE_AIR = 0X61; //空气质量
    public static final int DEVICE_TYPE_THL = 0X63; //温湿度&光照
    public static final int DEVICE_TYPE_SPHY = 0X71;  //  三合一设备
    public static final int DEVICE_TYPE_KEY = 0X72;  // 按键
    // 播放
    public static int MESSAGE_TYPE_PLAY = 0X01;//立即播放
    public static int MESSAGE_TYPE_PLAY_WAIT = 0X02;//点击播放
    public static int MESSAGE_TYPE_PLAY_WHILE = 0X03;//循环播放
    //  播放完是否显示到主界面
    public static int MESSAGE_TYPE_SCREEN_SHOW = 0X01;//不显示
    public static int MESSAGE_TYPE_SCREEN_UNSHOW = 0X02;//显示
    // main界面
    public final static int ZIGBEE_JOIN = 0X02;
//    public final static int OPEN_CAMERA = 0X03;
    public final static int ZIGBEE_JOIN_TIME = 60 * 1000;
    public final static int SET_ISCONN_VALUE = 5;
    public final static int MINI_GATEWAY_RECONN = 6;
    public final static int CHANGE_MAIN_LAYOUT_LL = 7;
    public final static int CHANGE_MAIN_LAYOUT_GIF = 8;
    public final static int CHANGE_MAIN_LAYOUT_VIDEO = 9;
    public final static int VIDEO_OVER = 10;

    // AP
    public static boolean IS_AP_SHUTDOWN = false;//热点是否开启

    // 登陆页面
    public final static int ACTIVITY_LOAD_INIT_DATA = 1;//初始化数据
    public final static int ACTIVITY_LOAD_TIME_OUT = 2;//超时检查
    public final static int ACTIVITY_LOAD_MINI_AP_START = 3;//开启热点
    public final static int ACTIVITY_LOAD_LIMITS_START = 4;//开启权限
    public final static int ACTIVITY_LOAD_WIFI_START = 5;//开启WIFI
    public final static int ACTIVITY_LOAD_LOGIN_SERVER_ERROR = 6;//登陆服务器失败
    public final static int ACTIVITY_LOAD_LOGIN_SERVER_DATA_ERROR = 7;//登陆服务器 数据格式错误
    public final static int ACTIVITY_LOAD_GET_PATIENTS_ERROR = 8;//获取患者失败
    public final static int ACTIVITY_LOAD_MINI_GATEWAY_OPEN = 9;//小网关已经断开
    public final static int ACTIVITY_LOAD_SUCCESS = 10;//小网关连接成功
    public final static int ACTIVITY_LOAD_INIT_VIEW = 11;//初始化视图
    public final static int ACTIVITY_LOAD_INTENT_MAIN_ACTIVITY = 12;//登陆主界面
    public final static int ACTIVITY_LOAD_NETWORK_AVAILABLE = 13;//网络是否有效
    public final static int ACTIVITY_LOAD_SERVER_CONFIG = 14;//获取服务端配置


    // 间隔时间
    public final static int DALAY_TIME_1_SECOND = 1000;// 1秒
    public final static int DALAY_TIME_2_SECOND = 2000;// 2秒
    public final static int DALAY_TIME_3_SECOND = 3000;// 3秒
    public final static int DALAY_TIME_4_SECOND = 4000;// 4秒
    public final static int DALAY_TIME_5_SECOND = 5000;// 4秒
    public final static int DALAY_TIME_10_SECOND = 10000;// 10秒
    public final static int DALAY_TIME_20_SECOND = 20000;// 20秒
    public final static int DALAY_TIME_30_SECOND = 30000;// 30秒
    public final static int DALAY_TIME_40_SECOND = 40000;// 50秒
    public final static int DALAY_TIME_2_MINUTE = 2000 * 60;// 2分钟
    public final static int DALAY_TIME_1_MINUTE = 1000 * 60;// 1分钟
    public final static int DALAY_TIME_5_MINUTE = 5 * 1000 * 60;// 1分钟


    //小网关
    public final static String MINI_GATEWAY_CONN_SUCC = "connecting";// 连接成功
    public final static String MINI_GATEWAY_CONN_START = "starting";// 开始连接
    public final static String MINI_GATEWAY_CONN_DIS = "disconnect";// 断开连接
    public final static int MINI_GATEWAY_REPORT = 1;// 小网关上报

    // 加载视频
    public final static int MAIN_FRAGMENT_GIF_START = 2;// 开始动画
    public final static int MAIN_FRAGMENT_GIF_STOP = 3;// 停止
    public final static int MAIN_FRAGMENT_MP_START = 4;//显示折线图







    public final static int LANGUAGE_TYPE_MINA = 3;
    public final static int LANGUAGE_TYPE_YUE = 2;
    public final static int LANGUAGE_TYPE_CH = 1;


    //支付状态

    public final static int PAY_SUCCESS = 1;
    public final static int PAY_NOT = 0;
    public final static int PAY_CANCEL = 2;


    public final static String TYPE_PAY_WECHAT = "wx";
    public final static String TYPE_PAY_ALIPAY = "alipay";


    public static final String ORDER_PAY = "PAY";
    public static final String ORDER_UPDATE = "UPDATE";
    public static final String ORDER_CANCEL = "CANCEL";
    public static final String ORDER_ADD = "ADD";


    public final static String DIDEASE_DIABETES = "DISEASES_DIABETES"; //糖尿病
    public final static String DIDEASE_BLOOD_PRESSURE = "DISEASES_HIGH_BLOOD_PRESSURE";



    public final static String APP_CACHE_DIR="/APP_CACHE_DIR";
    public final static String APP_CACHE_DB="/APP_CACHE_DB";


//    public static String WebBaseUrl(){
////       String   baseUrl = ( + "operateWay=0&");
//        AppContext mAppContext = (AppContext) AppUtil.getApp().getApplicationContext();
//        bsBuilder.append("patientCode=").append(mAppContext.getPatient().getPatientCode());
//
//      return  bsBuilder.toString();
//    }

    public static String serialNum =  android.os.Build.SERIAL;
//    public static String serialNum = "JYT20191020000329";


}
