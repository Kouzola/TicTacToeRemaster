package com.example.tictactoeremaster;

import android.content.Context;
import android.os.Handler;
import android.view.View;

public class ProgressiveDrawingAnimator {

    interface AnimatorListener{
        void onFinish();
        void onUpdate();
    }

    private AnimatorListener listener;
    private boolean isFinish;
    private boolean isDrawing;
    private long duration;
    private int progressiveValue;

    private int maxValue;
    private View view;


    public ProgressiveDrawingAnimator(View view){
        this.view = view;
        isFinish = true;
        isDrawing = false;
    }


    public void start(){
        if(!isDrawing && isFinish){
            draw();
        }
    }


    private void draw(){
        progressiveValue = 0;
        isDrawing = true;
        isFinish = false;
        Handler h = new Handler();
        h.post(new Runnable() {
            @Override
            public void run() {
                progressiveValue += 20;
                listener.onUpdate();
                if(progressiveValue < maxValue){
                    view.getHandler().postDelayed(this,duration);
                }else{
                    isFinish = true;
                    isDrawing = false;
                    listener.onFinish();
                }
            }
        });
    }

    public boolean isFinish() {
        return isFinish;
    }

    public boolean isDrawing() {
        return isDrawing;
    }

    public int getProgressiveValue() {
        return progressiveValue;
    }

    public void setAnimatorListener(AnimatorListener listener){
        this.listener = listener;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}
