package com.example.myapplication3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class JoystickView extends View {
    private int centerX, centerY;
    private int baseRadius, hatRadius;
    private int joystickX, joystickY;

    // Declare the OnMoveListener field
    private OnMoveListener onMoveListener;

    public JoystickView(Context context) {
        super(context);
        init();
    }

    public JoystickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        joystickX = centerX;
        joystickY = centerY;
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        baseRadius = Math.min(getWidth(), getHeight()) / 3;
        hatRadius = baseRadius / 3;
        joystickX = centerX;
        joystickY = centerY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setARGB(255, 50, 50, 50);

        // Draw the base circle
        canvas.drawCircle(centerX, centerY, baseRadius, paint);

        // Draw the joystick (hat)
        paint.setARGB(255, 150, 0, 0);
        canvas.drawCircle(joystickX, joystickY, hatRadius, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float distance = (float) Math.sqrt(Math.pow(event.getX() - centerX, 2) + Math.pow(event.getY() - centerY, 2));
        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
            if (distance < baseRadius) {
                joystickX = (int) event.getX();
                joystickY = (int) event.getY();
            } else {
                joystickX = (int) (centerX + (event.getX() - centerX) * baseRadius / distance);
                joystickY = (int) (centerY + (event.getY() - centerY) * baseRadius / distance);
            }

            // Notify listener
            if (onMoveListener != null) {
                int xPercent = (joystickX - centerX) * 100 / baseRadius;
                int yPercent = (joystickY - centerY) * 100 / baseRadius;
                onMoveListener.onMove(xPercent, yPercent);
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            joystickX = centerX;
            joystickY = centerY;
            if (onMoveListener != null) {
                onMoveListener.onMove(0, 0);
            }
        }
        invalidate();
        return true;
    }

    // Define the OnMoveListener interface
    public interface OnMoveListener {
        void onMove(int xPercent, int yPercent);
    }

    // Setter for the OnMoveListener
    public void setOnMoveListener(OnMoveListener listener) {
        this.onMoveListener = listener;
    }
}