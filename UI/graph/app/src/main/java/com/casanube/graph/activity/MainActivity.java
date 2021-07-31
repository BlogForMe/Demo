package com.casanube.graph.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.casanube.graph.R;
import com.casanube.graph.view.CircleWidgetView;
import com.casanube.graph.view.DashboardView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private final Timer timer = new Timer();

//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            String value = ("" + Math.random() * 15);
//            dashboardView.setValue(levels, value.substring(0, Math.min(4, value.length())), "午餐后1小时");
//        }
//    };

//    TimerTask task = new TimerTask() {
//        @Override
//        public void run() {
//            Message message = new Message();
//            message.what = 1;
//            handler.sendMessage(message);
//        }
//    };

    private DashboardView dashboardView;

    float level0[] = new float[]{3.9f, 7.0f, 8.4f};
    float level1[] = new float[]{3.9f, 9.4f, 11.1f};
    float level2[] = new float[]{3.9f, 7.8f, 11.1f};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics dm = getResources().getDisplayMetrics();


        CircleWidgetView circleWidgetView = findViewById(R.id.circleWidget);
        circleWidgetView.setValue("85%");

        dashboardView = findViewById(R.id.dashboardView);
        dashboardView.setValue(level2, "6.2", "午餐后1小时","偏低");


//        timer.schedule(task, 2000, 5000);
    }

}
