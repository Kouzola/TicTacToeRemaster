package com.example.tictactoeremaster;

import static com.example.tictactoeremaster.GrilleModel.CROIX;
import static com.example.tictactoeremaster.GrilleModel.ROND;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.List;

public class GrilleView extends View implements ProgressiveDrawingAnimator.AnimatorListener{


    private Paint paint;
    private int cellSize;
    private GameLogic gameLogic;
    private Boolean isCross = false;
    private Boolean isCross2 = false;
    private Boolean isCircleDrawing = false;
    boolean isFinish = false;
    private int rowForAnimatedDrawing, colForAnimatedDrawing;
    private int x1, x2, y1, y2, x21, x22, y21, y22;
    private int anglePos;
    private int counter, counter2;
    private boolean isDrawing = false;
    private Canvas canvas;
    private ProgressiveDrawingAnimator animator;

    private List<Integer> winnerCoordinates;

    public GrilleView(Context context) {
        super(context);
        init(null);
    }

    public GrilleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GrilleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public GrilleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        counter = 0;
        animator = new ProgressiveDrawingAnimator(this);
        animator.setAnimatorListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!animator.isDrawing() && !gameLogic.isGameFinished()) {
            float posX = event.getX();
            float posY = event.getY();
            int action = event.getAction();

            if (action == MotionEvent.ACTION_DOWN) {
                int row = (int) posY / (cellSize);
                int col = (int) posX / (cellSize);
                switch (gameLogic.getTurn()) {
                    case CROIX:
                        if (gameLogic.tryPlaceMarker(CROIX, row, col)) {
                            counter = 1;
                            counter2 = 1;
                            progressiveCrossDrawing(row, col);
                            isCross = true;
                        }
                        break;
                    case ROND:
                        if (gameLogic.tryPlaceMarker(ROND, row, col)) {
                            progressiveCircleDrawing(row, col);
                            anglePos = 0;
                            isCircleDrawing = true;
                        }
                        break;
                }
            }
        }
        else if(gameLogic.isGameFinished()){
            gameLogic.resetGame();
            resetAnimCoordinate();
            invalidate();
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        super.onDraw(canvas);
        cellSize = getWidth() / 3;
        for (int i = 1; i <= 2; i++) {
            canvas.drawLine(0.0f, cellSize * i, getWidth(), cellSize * i, paint);
            canvas.drawLine(cellSize * i, 0.0f, cellSize * i, getHeight(), paint);
        }
        drawXorO(canvas);
        if(gameLogic.isGameFinished() && !gameLogic.isEvenMatch()){
            drawWinningLine(canvas);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int dim = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));

        setMeasuredDimension(dim, dim);
    }

    public void drawX(Canvas canvas, int row, int col) {
        if (row == rowForAnimatedDrawing && col == colForAnimatedDrawing) {
            canvas.drawLine(x1, y1, x2, y2, paint);
            canvas.drawLine(x21, y21, x22, y22, paint);
            return;
        }
        canvas.drawLine(col * cellSize, row * cellSize + cellSize, col * cellSize + cellSize, row * cellSize, paint);
        canvas.drawLine(col * cellSize, row * cellSize, col * cellSize + cellSize, row * cellSize + cellSize, paint);


    }

    private void drawO(Canvas canvas, int row, int col) {
        if (row == rowForAnimatedDrawing && col == colForAnimatedDrawing) {
            canvas.drawArc(
                    (float) 10+col*cellSize,
                    (float) 10+row*cellSize,
                    (float) col*cellSize+cellSize-10,
                    (float) row*cellSize+cellSize-10,
                    (float) 0,
                    (float) anglePos,
                    false,paint);
            return;
        }
        canvas.drawCircle(col*cellSize+cellSize/2, row*cellSize+cellSize/2, (cellSize/2)-10, paint);
    }

    public void setGameLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    private void setLine1(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    private void setLine2(int x21, int y21, int x22, int y22) {
        this.x21 = x21;
        this.x22 = x22;
        this.y21 = y21;
        this.y22 = y22;
    }

    private void setCircleCoord(int anglePos) {
        this.anglePos = anglePos;
    }

    private void drawXorO (Canvas canvas){
        int[][] board = gameLogic.getGameBoard();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == CROIX) drawX(canvas, i, j);
                else if(board[i][j] == ROND) drawO(canvas,i,j);
            }
        }

        invalidate();

    }

    public void setRowColForAnim ( int row, int col){
        rowForAnimatedDrawing = row;
        colForAnimatedDrawing = col;
    }

    public void progressiveCrossDrawing (int row, int col){
        setRowColForAnim(row, col);
        animator.setDuration(1L);
        animator.setMaxValue(cellSize);
        animator.start();
    }

    private void progressiveCircleDrawing (int row, int col){
        setRowColForAnim(row, col);
        animator.setDuration(1L);
        animator.setMaxValue(360);
        animator.start();
    }

    public void drawWinningLine(Canvas canvas){
        canvas.drawLine(winnerCoordinates.get(0)*cellSize+cellSize/2,winnerCoordinates.get(1)*cellSize+cellSize/2,winnerCoordinates.get(2)*cellSize+cellSize/2,winnerCoordinates.get(3)*cellSize+cellSize/2,paint);
        invalidate();
    }

    public void setWinnerCoordinates(List<Integer> coordinates){
        winnerCoordinates = coordinates;
    }

    private void resetAnimCoordinate(){
        setLine1(0,0,0,0);
        setLine2(0,0,0,0);
    }

    @Override
    public void onFinish() {
        //if isCircleDrawing then isCircleDrawing = false; et pour les autres aussi
        if(isCircleDrawing){
            isCircleDrawing = false;
            return;
        }
        if(isCross){
            isCross = false;
            isCross2 = true;
            this.progressiveCircleDrawing(rowForAnimatedDrawing,colForAnimatedDrawing);
            return;
        }
        if(isCross2) isCross2 = false;
    }

    @Override
    public void onUpdate() {
        int progressiveValue = animator.getProgressiveValue();
        if(isCircleDrawing) setCircleCoord(progressiveValue);
        if(isCross) setLine1((colForAnimatedDrawing * cellSize),
                            (rowForAnimatedDrawing * cellSize),
                            (progressiveValue + cellSize * colForAnimatedDrawing),
                            (progressiveValue + cellSize * rowForAnimatedDrawing));
        if(isCross2) setLine2(colForAnimatedDrawing * cellSize,
                        rowForAnimatedDrawing * cellSize + cellSize,
                        progressiveValue + cellSize * colForAnimatedDrawing,
                        cellSize * rowForAnimatedDrawing + cellSize - progressiveValue);
        invalidate();
    }
}




//TODO : Refactoriser toutes la classe pour simplifier le bail
//TODO : Faire animator pour la ligne gagnante
