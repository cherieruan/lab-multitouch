package edu.uw.multitouchlab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";

    private DrawingSurfaceView view;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (DrawingSurfaceView)findViewById(R.id.drawingView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY() - getSupportActionBar().getHeight(); //closer to center...

        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);

        int action = event.getActionMasked();
        switch(action) {
            case (MotionEvent.ACTION_POINTER_DOWN) : {
                view.addTouch(pointerId, event.getX(pointerIndex), event.getY(pointerIndex));
                Log.v(TAG, pointerId + " finger down");
                return true;
            }
            case (MotionEvent.ACTION_DOWN) : //put finger down
                view.addTouch(pointerId, event.getX(pointerIndex), event.getY(pointerIndex));
                return true;
            case (MotionEvent.ACTION_MOVE) : //move finger
                //Log.v(TAG, "finger move");
                for (int i = 0; i < event.getPointerCount(); i++) {
                    int pId = event.getPointerId(i);
                    view.moveTouch(pId, event.getX(i), event.getY(i));
                }

                return true;
            case MotionEvent.ACTION_POINTER_UP: {
                Log.v(TAG, pointerId + " finger lifted");
                view.removeTouch(pointerId);
                return true;
            }
            case (MotionEvent.ACTION_UP) : //lift finger up
                view.removeTouch(pointerId);
                return true;
            case (MotionEvent.ACTION_CANCEL) : //aborted gesture
            case (MotionEvent.ACTION_OUTSIDE) : //outside bounds
            default :
                return super.onTouchEvent(event);
        }
    }
}