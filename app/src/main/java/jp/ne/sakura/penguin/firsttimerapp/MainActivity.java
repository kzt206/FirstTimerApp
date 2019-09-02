package jp.ne.sakura.penguin.firsttimerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Runnable{

    private long startTime;

    private TextView timerText;
    private Button startButton;
    private TextView panel;

    private final Handler handler = new Handler();
    private volatile boolean stopRun = false;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss.S", Locale.JAPAN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = findViewById(R.id.timer);
        timerText.setText(dateFormat.format(0));

        panel = findViewById(R.id.panel);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener(){
            Thread thread;
            @Override
            public void onClick(View v){
                stopRun = false;
                thread = new Thread(MainActivity.this);
                thread.start();

                startTime = System.currentTimeMillis();

                panel.setBackgroundColor(Color.BLUE);
            }
        });

        Button stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                stopRun = true;
                timerText.setText(dateFormat.format(0));
                panel.setBackgroundResource(R.drawable.frame_style);
            }
        });

    }



//    @Override
//    public void onClick(View v){
//        Thread thread;
//        if(v == startButton){
//            stopRun = false;
//            thread = new Thread(this);
//            thread.start();
//
//            startTime = System.currentTimeMillis();
//        }else{
//            stopRun = true;
//            timerText.setText(dateFormat.format(0));
//        }
//
//    }

    @Override
    public void run(){
        // 100 msec order
        int period = 100;

        while(!stopRun){
            // sleep: period msec
            try{
                Thread.sleep(period);
            }catch (InterruptedException e){
                e.printStackTrace();
                stopRun = true;
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    long endTime = System.currentTimeMillis();
                    long diffTime = (endTime - startTime);

                    timerText.setText(dateFormat.format(diffTime));
                }
            });


        }


    }


}
