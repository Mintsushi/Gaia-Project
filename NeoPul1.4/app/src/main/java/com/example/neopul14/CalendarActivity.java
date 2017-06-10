package com.example.neopul14;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by 리제 on 2017-05-09.
 */

///////////////////////// 미구현 /////////////////////////////

public class CalendarActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    GestureDetector detector;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        count = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        detector = new GestureDetector(this, this);

    }

    public boolean onTouchEvent(MotionEvent motionEvent){
        return detector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Toast.makeText(this,"[Touch] X = " + e.getX() + " / Y = " + e.getY(),Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float x1 = e1.getX(); //40
        float x2 = e2.getX(); //140

        if((x1-x2) > 100 && count ==0 ){
            count = 1;
            startActivity(new Intent(CalendarActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            finish();
            return false;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Toast.makeText(this,"[LongPress] X = " + e.getX() + " / Y = " + e.getY(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        return false;
    }

}
