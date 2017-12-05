package classmap.classtest.com.hanjo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;



public class MainActivity extends AppCompatActivity {

    ImageButton startBtn;
    ImageButton exitBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//FullScreen
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//화면 회전 주기.
        setContentView(R.layout.activity_main);


        startBtn= (ImageButton) findViewById(R.id.startBtn);
        exitBtn=(ImageButton) findViewById(R.id.exitBtn);


        startBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        startBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return false;
            }
        });


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent changeView = new Intent(MainActivity.this, GameActivity.class);
                changeView.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                changeView.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //음악 정지 걸고
                startActivity(changeView); //뷰의 전환
            }
        });



        exitBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return false;
            }
        });

        exitBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return false;
            }
        });


        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }






}
