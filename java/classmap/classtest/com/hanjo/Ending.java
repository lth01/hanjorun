package classmap.classtest.com.hanjo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

/**
 * Created by Roh Ji on 2017-12-03.
 */

public class Ending extends Activity {


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//FullScreen
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//화면 회전 주기.
            setContentView(R.layout.ending);


        try{
            Thread.sleep(3000);
        }catch(Exception e)
            {
                e.printStackTrace();
            }

            finish();
        }
    }

