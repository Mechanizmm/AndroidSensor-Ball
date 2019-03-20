package pl.pp.sensor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Random;

public class Ball extends View
{
    private Paint p;

    private DisplayMetrics metrics = this.getResources().getDisplayMetrics();
    private int width = metrics.widthPixels;
    private int height = metrics.heightPixels - 300;

    private int xpos = width/2;
    private int ypos = height/2;
    private int vx;
    private int vy;
    private int radius = 50;

    private int bigRadius = width/2 - width/15;

    private int windX = 0;
    private int windY = 0;
    private int windTimes = 30;

    private boolean isGameOver = false;

    public Ball(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //draw Ball
        p.setStyle(Paint.Style.FILL);
        canvas.drawCircle(xpos, ypos, radius, p);

        //draw Field
        p.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(width/2, height/2, bigRadius, p);

        //draw Arrow
        p.setStrokeWidth(10);
        canvas.drawLine(width/2, 200, width/2-windX*25, 200+windY*25, p);
        p.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width/2-windX*25, 200+windY*25, 10, p);

    }

    public void move()
    {
        //odległość potrzebna do przekroczenia linii
        int distance =  ( ((xpos - width/2)*(xpos - width/2)) + ((ypos - height/2)*(ypos - height/2)) );

        if(distance > (radius + bigRadius)*(radius + bigRadius) )
        {
            xpos = width/2;
            ypos = height/2;
            windX = 0;
            windY = 0;

            isGameOver = true;
        }

        doWind();

        xpos -= vx+windX;
        ypos += vy+windY;
    }

    public void doWind()
    {
        if(windTimes == 0) {

            Random r = new Random();

            boolean wx = r.nextBoolean();
            if (wx && windX < 5)
                windX++;
            else if (!wx && windX > -5)
                windX--;

            boolean wy = r.nextBoolean();
            if (wy && windY < 5)
                windY++;
            else if (!wy && windY > -5)
                windY--;

            windTimes = 30;
        }

        windTimes--;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void resetGameState() {
        isGameOver = false;
    }

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }
}

