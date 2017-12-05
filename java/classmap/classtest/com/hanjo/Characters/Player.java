package classmap.classtest.com.hanjo.Characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import classmap.classtest.com.hanjo.GamePamel.GamePanel;
import classmap.classtest.com.hanjo.R;
import classmap.classtest.com.hanjo.TileMap.TileMap;




//캐릭터의 좌표 문제 display.y/5를 할 경우의 에러 때문에 통통 튀는 문제가 있음.
//현재는 점프는 되나 땅에 박힌다.

public class Player {

    Bitmap picture[];
    TileMap tileMap;
    int currentMap;
    boolean aniClimb;


    Point displaySize;


    private int hp;

    private double x;
    private double y;
    private int width;
    private int height;


    //속도와 현재 상태 관련.
    private double maxSpeed;
    private double maxFallingSpeed;
    private double jumpStart;
    private double gravity;
    private double climbSpeed;


    private boolean isJump;
    private boolean isClimb;
    private boolean isFalling;

    //충돌 판정 변수
    private boolean topLeft;
    private boolean topRight;
    private boolean bottomLeft;
    private boolean bottomRight;



    private double dy;
    private double dx;

    private Animation animation;
    private Bitmap[] dyingSprites;
    private Bitmap[] walkingSprites;
    private Bitmap[] jumpingSprites;
    private Bitmap[] fallingSprites;
    private Bitmap[] climbingSprites;

    private GamePanel gamePanel;


    public Player(Context context, TileMap tileMap, GamePanel gamePanel)
    {
        this.gamePanel=gamePanel;
        this.tileMap = tileMap;
        hp=200;
        gravity=0.64;
        maxSpeed=8.4;
        jumpStart=-20;
        maxFallingSpeed=12;
        climbSpeed=-maxSpeed;
        picture=new Bitmap[3];
        picture[0]=BitmapFactory.decodeResource(context.getResources(), R.drawable.running);//run
        picture[1]=BitmapFactory.decodeResource(context.getResources(), R.drawable.diying);//dying
        picture[2]=BitmapFactory.decodeResource(context.getResources(), R.drawable.climbing);//climb

        animation = new Animation();
        dyingSprites=new Bitmap[7];
        walkingSprites= new Bitmap[5];
        jumpingSprites= new Bitmap[1];
        fallingSprites=new Bitmap[1];
        climbingSprites = new Bitmap[2];

        jumpingSprites[0]=BitmapFactory.decodeResource(context.getResources(), R.drawable.jumping);
        fallingSprites[0]=BitmapFactory.decodeResource(context.getResources(), R.drawable.falling);




        int wTileSize=picture[0].getWidth()/5;
        int dTileSize=picture[1].getWidth()/7;
        int cTileSize=picture[2].getWidth()/2;

        for(int i=0; i<5; i++)
        {
            walkingSprites[i]=Bitmap.createBitmap(picture[0],i*wTileSize,0,picture[0].getWidth()/5, picture[0].getHeight());
        }
        for(int i=0; i<7; i++)
        {
            dyingSprites[i]=Bitmap.createBitmap(picture[1],i*dTileSize , 0, picture[1].getWidth()/7,picture[1].getHeight());

        }
        for(int i=0;i<2; i++)
        {
            climbingSprites[i]=Bitmap.createBitmap(picture[2], i*cTileSize, 0 , picture[2].getWidth()/2, picture[2].getHeight());
        }
    }


    public void init()
    {

        for(int i=0; i<7; i++)
            dyingSprites[i]= Bitmap.createScaledBitmap(dyingSprites[i], width, height, false);
        for(int i=0; i<5; i++)
            walkingSprites[i]=Bitmap.createScaledBitmap(walkingSprites[i], width, height, false);
        for(int i=0; i<2; i++)
            climbingSprites[i]=Bitmap.createScaledBitmap(climbingSprites[i], width, height, false);
        jumpingSprites[0]=Bitmap.createScaledBitmap(jumpingSprites[0], width, height, false);
        fallingSprites[0]=Bitmap.createScaledBitmap(fallingSprites[0], width, height, false);

        x=300;
        y=300;
        dx=maxSpeed;
        currentMap=0;
        while(true)
        {
            y=y+1;
            calculateCorners(x, y);
            if(bottomLeft || bottomRight)
                break;



        }
        y=y-2;

    }




    public void setx(int i) {        x = i;    }

    public void sety(int i) {        y = i;    }


    public void setJumping()
    {
        if(!isFalling)
        {
            isJump = true;
        }


    }

    public void setClimb(boolean b)
    {
        isClimb=b;
    }

    public void update() {

        dx += maxSpeed;
        if (dx > maxSpeed) {
            dx = maxSpeed;
        }


        if (isJump) {
            dy = jumpStart;
            isFalling = true;
            isJump = false;
        }
        if (isFalling) {
            dy += gravity;
            if (dy > maxFallingSpeed) {
                dy = maxFallingSpeed;
            }
        } else {
            dy = 0;
        }


        // check collisions

        int currCol = tileMap.getColTile((int) x);
        int currRow = tileMap.getRowTile((int) y);

        double tox = x + dx;
        double toy = y + dy;

        double tempx = x;
        double tempy = y;

        calculateCorners(x, toy);

        if (dy < 0) {

            if (topLeft || topRight) {
                dy = 0;
                tempy = currRow * tileMap.getTileSize() + height / 2;
            } else {
                tempy += dy;
            }

        }
        if (dy > 0) {
            if (bottomLeft || bottomRight) {
                dy = 0;
                isFalling = false;
                tempy = (currRow + 1) * tileMap.getTileSize() - height / 2;
            } else {
                tempy += dy;
            }
        }

        calculateCorners(tox, y);

        if (dx < 0) {
            if (topLeft || bottomLeft) {
                dx = 0;
                tempx = currCol * tileMap.getTileSize() + width / 2;
            } else {
                tempx += dx;
            }
        }

        if (dx > 0) {
            if (topRight || bottomRight) {
                dx = 0;
                tempx = (currCol + 1) * tileMap.getTileSize() - width / 2;
            } else {
                tempx += dx;
            }
        }
        aniClimb=false;

            if (isClimb) {
                if (topRight || bottomRight) {
                    tempy += climbSpeed;
                    if (dy > climbSpeed)
                        dy = climbSpeed;
                    aniClimb=true;
                }
            }




        if (!isFalling) {
            calculateCorners(x, y + 1);
            if (!bottomLeft && !bottomRight) {
                isFalling = true;

            }
        }

        x = tempx;
        y = tempy;

        /// 맵 위치 정하기

        tileMap.setX((int) (displaySize.x/ 2 - x));
        tileMap.setY((int) (displaySize.y / 2 - y));

        animation.setFrames(walkingSprites);
        animation.setDelay(50);

        if (dy < 0) {
            animation.setFrames(jumpingSprites);
            animation.setDelay(-1);
        }
        if (dy > 0) {
            animation.setFrames(fallingSprites);
            animation.setDelay(-1);
        }

        if(aniClimb)
        {
            animation.setFrames(climbingSprites);
            animation.setDelay(70);
        }

        animation.update();

        if(tileMap.getTile(0,tileMap.getRowTile((int) y),tileMap.getColTile((int) x))==4)
            gamePanel.setRunning(false);

    }

    private void calculateCorners(double x, double y) {
        int leftTile = tileMap.getColTile((int) (x - width / 2));
        int rightTile = tileMap.getColTile((int) (x + width / 2)-1);
        int topTile = tileMap.getRowTile((int) (y - height / 2));
        int bottomTile = tileMap.getRowTile((int) (y + height / 2)-1);

        topLeft = tileMap.isBlocked(0,topTile, leftTile);
        topRight = tileMap.isBlocked(0,topTile, rightTile);
        bottomLeft = tileMap.isBlocked(0,bottomTile, leftTile);
        bottomRight = tileMap.isBlocked(0,bottomTile, rightTile);
    }


    public void draw(Canvas canvas, Paint p)
    {
        int tx = tileMap.getX();
        int ty = tileMap.getY();
       // canvas.drawBitmap(animation.getImage(),(int) (tx+x-width/2), (int)(ty+y-height/2), p);
    }


    public void setDisplaySize(Point p) //캐릭터의 크기와 화면 고정.
    {
        displaySize = p;
        width=displaySize.x/10;
        height=displaySize.x/10;




    }




}