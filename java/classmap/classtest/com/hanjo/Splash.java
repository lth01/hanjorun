package classmap.classtest.com.hanjo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.WindowManager;

/**
 * Created by Roh Ji on 2017-12-01.
 */

public class Splash extends Activity {
    private final int SPLASH_DISPLAY_LENGTH = 2500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//FullScreen
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//화면 회전 주기.
        setContentView(R.layout.splash);



        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* 메뉴액티비티를 실행하고 로딩화면을 죽인다.*/
                Intent mainIntent = new Intent(Splash.this,MainActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
