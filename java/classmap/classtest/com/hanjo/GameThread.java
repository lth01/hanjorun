package classmap.classtest.com.hanjo;

import android.os.Handler;
import android.os.Message;

import classmap.classtest.com.hanjo.GamePamel.GamePanel;



/**
 * Created by Roh Ji on 2017-11-22.
 */

public class GameThread extends Thread{

    GamePanel gamePanel;
    Handler handler;
    private int targetTime;
    private boolean running;


    public GameThread(Handler handle, GamePanel game)
    {
        handler = handle;
        gamePanel = game;

    }



    @Override
    public void run() {
        super.run();
        gamePanel.init();
        long startTime;
        long urdTime;
        long waitTime;

        while(gamePanel.getRunning())
        {
            targetTime = 1000 / gamePanel.FPS;
            Message msg = new Message();
            msg.what = 1;
            try{


                startTime = System.nanoTime();
                urdTime = (System.nanoTime() - startTime) / 1000000;
                waitTime = targetTime - urdTime;
                   Thread.sleep(waitTime);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
                handler.sendMessage(msg);

        }
        Message msg = new Message();
        msg.what=2;
        handler.sendMessage(msg);

    }
}
