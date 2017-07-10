package com.example.mint.progress;

        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.os.BatteryManager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.view.Window;
        import android.widget.Button;
        import android.widget.ProgressBar;
        import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    int ProgressStatus = 50;
    ProgressBar ProgressBar;
    TextView TextViewPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Request window feature action bar
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button m10; Button m1; Button p1; Button p10;

        ProgressBar = (ProgressBar) findViewById(R.id.pb);
        TextViewPercentage = (TextView) findViewById(R.id.tv_percentage);
        m10 = (Button)findViewById(R.id.m10);
        m1 = (Button)findViewById(R.id.m1);
        p1 = (Button)findViewById(R.id.p1);
        p10 = (Button)findViewById(R.id.p10);

        TextViewPercentage.setText(Integer.toString(ProgressStatus)+"%");
        ProgressBar.setProgress(ProgressStatus);

        m10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ProgressStatus > 0) {
                    ProgressStatus = ProgressStatus - 10;

                    TextViewPercentage.setText(Integer.toString(ProgressStatus) + "%");
                    ProgressBar.setProgress(ProgressStatus);
                }
            }
        });

        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ProgressStatus > 0) {
                    ProgressStatus = ProgressStatus - 1;

                    TextViewPercentage.setText(Integer.toString(ProgressStatus) + "%");
                    ProgressBar.setProgress(ProgressStatus);
                }
            }
        });
        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ProgressStatus < 100) {
                    ProgressStatus = ProgressStatus + 1;

                    TextViewPercentage.setText(Integer.toString(ProgressStatus) + "%");
                    ProgressBar.setProgress(ProgressStatus);
                }
            }
        });
        p10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ProgressStatus < 100) {
                    ProgressStatus = ProgressStatus + 10;

                    TextViewPercentage.setText(Integer.toString(ProgressStatus) + "%");
                    ProgressBar.setProgress(ProgressStatus);
                }
            }
        });




    }
}