package classmap.classtest.com.hanjo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import classmap.classtest.com.hanjo.GamePamel.GamePanel;



/**
 * Created by Roh Ji on 2017-11-16.
 */

public class GameActivity extends Activity{

    ImageButton jump;
    ImageButton climb;
    Button btn;
    GameThread gameThread;
    Display display; //화면 사이즈 측정

    MediaPlayer mp;

    Canvas canvas;

    GamePanel gamePanel;

    Handler mHandle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//FullScreen
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//화면 회전 주기.
        setContentView(R.layout.gamepanel);



        display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        canvas= new Canvas();
        btn = (Button) findViewById(R.id.menu);
        gamePanel = (GamePanel) findViewById(R.id.panel);
        jump = (ImageButton) findViewById(R.id.jump);
        climb = (ImageButton) findViewById(R.id.climb);

        gamePanel.setDisplaySize(size);


        mHandle=new Handler(){
          @Override
          public void handleMessage(Message msg)
          {
            if(msg.what == 1)
            {
                gamePanel.update();
                gamePanel.invalidate();
                gamePanel.draw(canvas);

            }
            if(msg.what==2)
            {

                finish();
            }


          }
        };


        gameThread = new GameThread(mHandle, gamePanel);
        gameThread.start();
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamePanel.setJump();
            }
        });

        climb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gamePanel.setClimb(true);
                return false;
            }
        });
        climb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamePanel.setClimb(false);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamePanel.setRunning(false);
                onPause();
                finish();
            }
        });





    }
    public void onStart(){
        super.onStart();
        mp=MediaPlayer.create(getApplicationContext(),R.raw.a);
        mp.start();
    }

    public void onPause() //게임오버 시 화면 전환 애니메이션 빼기.
    {
        super.onPause();
        overridePendingTransition(0, 0);

    }
    @Override
    public void onBackPressed() //뒤로가기 (임시로 막아놓기)
    {
        //super.onBackPressed();
    }


}
