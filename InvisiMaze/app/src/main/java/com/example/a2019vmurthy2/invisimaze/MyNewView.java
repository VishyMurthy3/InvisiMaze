package com.example.a2019vmurthy2.invisimaze;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MyNewView extends View implements Runnable{

    /* ------------------------*/
    /*    member variables     */

    private Handler mHandler;
    private int mCount =0;

    private final static int MAX_POINTS = 10000000;

    private Paint mPaint;
    private int mPointIndx;

    private static ArrayList<ArrayList<Point>> mPointList;
    private int mStrandCount = 0;

    private boolean mBufferIsCirculating;


    /* ------------------------*/
    /*    constructor          */

    public MyNewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHandler = new Handler();
        mCount = 0;
        setupPointListList();
        initializePaint();

    }

    /* ------------------------*/
    /*    class methods        */



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN :
                startCounting();
                makeNewCircularBuffer();
                addPointToCircularBuffer(new Point(Math.round(event.getX()), Math.round(event.getY())));
                this.setBackgroundColor(Color.WHITE);
                postInvalidate();
                break;
            case MotionEvent.ACTION_MOVE :
                addPointToCircularBuffer(new Point(Math.round(event.getX()), Math.round(event.getY())));

                break;
            case MotionEvent.ACTION_UP :
                stopCounting();
                this.setBackgroundColor( getResources().getColor(R.color.lblue) );
                postInvalidate();
                break;

        }
        return true;
    }

    /* --------------------------------*/
    /*    protected draw operation     */

    @Override
    protected void onDraw(Canvas canvas) {

        for (ArrayList<Point> circlePoints : mPointList) {
            // draw points
            for (Point p : circlePoints) {
                canvas.drawCircle(p.x, p.y, 5, mPaint);
            }
        }
    }

    /* ------------------------------------*/
    /*    custom initialization methods    */

    private void setupPointListList() {
        mPointList = new ArrayList<ArrayList<Point>>();
    }

    private void initializePaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.CYAN);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    /* ---------------------*/
    /*    custom methods    */

    private void makeNewCircularBuffer() {
        ArrayList<Point> circlePoints = new ArrayList<Point>();
        mPointList.add(circlePoints);
        mBufferIsCirculating = false;
        mPointIndx = 0;
        mStrandCount++;
    }

    private void incrementAndUpdate() {
        mCount++;
        postInvalidate();
    }

    public void run() {
        incrementAndUpdate();
        mHandler.postDelayed(this, 50);
    }

    public void startCounting() {
        mCount = 0;
        mHandler.postDelayed(this, 50);
    }

    public void stopCounting() {
        mHandler.removeCallbacks(this);
    }


    private void addPointToCircularBuffer(Point p){
        if (!mBufferIsCirculating) {
            mPointList.get(mStrandCount-1).add(p);
        } else {
            mPointList.get(mStrandCount-1).set(mPointIndx, p);
        }
        mPointIndx++;
        if (mPointIndx >= MAX_POINTS) {
            mBufferIsCirculating = true;
        }
        mPointIndx %= MAX_POINTS;

    }

    public void clearCircles() {

        mPointList.clear();
        mStrandCount = 0;
        postInvalidate();
    }

}
