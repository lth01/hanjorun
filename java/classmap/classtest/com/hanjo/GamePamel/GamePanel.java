package classmap.classtest.com.hanjo.GamePamel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import classmap.classtest.com.hanjo.Characters.Player;
import classmap.classtest.com.hanjo.TileMap.Background;
import classmap.classtest.com.hanjo.TileMap.TileMap;

//실직적으로 화면이 동작한다.
public class GamePanel extends View{

    private TileMap tileMap;
    private Player player;
    private Background background;
    Point displaySize;
    private boolean running;
    public final static int FPS = 60;

    Paint painter;

    public GamePanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        background = new Background(context, (double)1);
        background.setVector((double)5, (double)5);
        tileMap = new TileMap(context);
        player = new Player(context, tileMap, this);
        painter = new Paint();

    }


    public void init()
    {
        tileMap.init();
        player.init();
        setRunning(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        background.draw(canvas, painter);
        tileMap.draw(canvas, painter);
        player.draw(canvas, painter);

    }


    public void update()
    {
        background.update();
        tileMap.update();
        player.update();


    }




    public void setJump()
    {
        player.setJumping();
    }

    public void setClimb(boolean b)
    {
        player.setClimb(b);
    }


    public void setDisplaySize(Point displaySize) //화면에 출력할 오브젝트 크기 설정.
    {
        this.displaySize=displaySize;
        background.setSize(displaySize);
        tileMap.setDisplaySize(displaySize);
        player.setDisplaySize(displaySize);

    }


    public void setRunning(boolean b)
    {
        running=b;
    }

    public boolean getRunning()
    {
        return running;
    }




}
