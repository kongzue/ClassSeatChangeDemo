package com.kongzue.classseatchangedemo;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.kongzue.classseatchangedemo.util.SScrollView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    public final int FOCUSCOLOR = Color.argb(100, 0, 133, 120);
    public final int EMPTYCOLOR = Color.argb(30, 0, 0, 0);
    
    private SScrollView scrollView;
    private GridLayout gridLayout;
    private TextView dropText;
    
    private List<View> views;
    
    private View onDropView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        scrollView = findViewById(R.id.scrollView);
        gridLayout = findViewById(R.id.gridLayout);
        dropText = findViewById(R.id.drop_text);
        
        views = new ArrayList<>();
        for (int i = 0; i < 20 * 20; i++) {
            final View item = LayoutInflater.from(this).inflate(R.layout.layout_item, null, false);
            TextView itemText = item.findViewById(R.id.item_text);
            
            if (i == 0 || i == 1 || i == 20 || i == 21 || i == 40 || i == 41 || i == 60 || i == 61) {
                itemText.setText(getLocPoint(i));
                itemText.setBackgroundColor(FOCUSCOLOR);
            } else {
                itemText.setText("");
                itemText.setBackgroundColor(EMPTYCOLOR);
            }
            item.setLongClickable(true);
            
            item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    TextView itemText = v.findViewById(R.id.item_text);
                    if (itemText.getText().toString().isEmpty()) return true;
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    v.setVisibility(View.INVISIBLE);
                    onDropView = v;
                    scrollView.computeScroll();
                    copy(v);
                    return true;
                }
            });
            
            item.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        touchDownLocation = new int[2];
                        touchDownLocation[0] = (int) event.getX();
                        touchDownLocation[1] = (int) event.getY();
                        
                        int[] locations = new int[2];
                        v.getLocationOnScreen(locations);
                        
                        dropText.setX(locations[0] + scrollView.getScrollX());
                        dropText.setY(locations[1] + scrollView.getScrollY());
                        return false;
                    }
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        if (onDropView != null) {
                            int[] centerPoint = new int[2];
                            centerPoint[0] = (int) (dropText.getX() + dropText.getWidth() / 2);
                            centerPoint[1] = (int) (dropText.getY() + dropText.getHeight() / 2);
                            
                            if (originalLocation == null) originalLocation = new int[2];
                            
                            dropText.setX(originalLocation[0] + event.getX() - touchDownLocation[0] - scrollView.getScrollX());
                            dropText.setY(originalLocation[1] + event.getY() - touchDownLocation[1] - scrollView.getScrollY());
                            
                            boolean screenMoveFlag = false;
                            int movDistanceX = 0, movDistanceY = 0;
                            if (dropText.getX() + dropText.getWidth() > scrollView.getWidth()) {
                                movDistanceX = dp2px(5);
                                screenMoveFlag = true;
                            }
                            if (dropText.getX() < 0) {
                                movDistanceX = dp2px(-5);
                                screenMoveFlag = true;
                            }
                            if (dropText.getY() + dropText.getHeight() > scrollView.getHeight()) {
                                movDistanceY = dp2px(5);
                                screenMoveFlag = true;
                            }
                            if (dropText.getY() < 0) {
                                movDistanceY = dp2px(-5);
                                screenMoveFlag = true;
                            }
                            
                            if (screenMoveFlag) {
                                scrollView.smoothScrollBy(movDistanceX, movDistanceY);
                            }
                        }
                        return true;
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                        if (onDropView != null) {
                            int[] centerPoint = new int[2];
                            centerPoint[0] = (int) (dropText.getX() + dropText.getWidth() / 2) + scrollView.getScrollX();
                            centerPoint[1] = (int) (dropText.getY() + dropText.getHeight() / 2) + scrollView.getScrollY();
                            
                            for (int i = 0; i < views.size(); i++) {
                                View item = views.get(i);
                                
                                int[] parentLocations = new int[2];
                                int[] locations = new int[2];
                                gridLayout.getLocationOnScreen(parentLocations);
                                item.getLocationOnScreen(locations);
                                int[] originalLocation = new int[2];
                                originalLocation[0] = locations[0] - parentLocations[0];
                                originalLocation[1] = locations[1] - parentLocations[1];
                                
                                if (centerPoint[0] > originalLocation[0] && centerPoint[0] < originalLocation[0] + item.getWidth()) {
                                    if (centerPoint[1] > originalLocation[1] && centerPoint[1] < originalLocation[1] + item.getHeight()) {
                                        TextView itemText = item.findViewById(R.id.item_text);
                                        if (itemText.getText().toString().isEmpty()) {
                                            itemText.setText(getLocPoint(i));
                                            itemText.setBackgroundColor(FOCUSCOLOR);
                                            
                                            TextView oldTextView = onDropView.findViewById(R.id.item_text);
                                            oldTextView.setText("");
                                            oldTextView.setBackgroundColor(EMPTYCOLOR);
                                        }
                                    }
                                }
                            }
                            
                            onDropView.setVisibility(View.VISIBLE);
                        }
                        dropText.setVisibility(View.GONE);
                        onDropView = null;
                    }
                    return false;
                }
            });
            
            gridLayout.addView(item);
            views.add(item);
        }
    }
    
    private String getLocPoint(int i) {
        int[] loc = new int[2];
        loc[0] = i / gridLayout.getRowCount() + 1;              //行
        loc[1] = i % gridLayout.getColumnCount() + 1;           //列
        return loc[1] + "," + loc[0];
    }
    
    private int[] touchDownLocation;
    private int[] originalLocation;
    
    private void copy(View v) {
        TextView itemText = v.findViewById(R.id.item_text);
        dropText.setVisibility(View.VISIBLE);
        dropText.setText(itemText.getText().toString());
        int[] parentLocations = new int[2];
        int[] locations = new int[2];
        gridLayout.getLocationOnScreen(parentLocations);
        v.getLocationOnScreen(locations);
        originalLocation = new int[2];
        originalLocation[0] = locations[0] - parentLocations[0];
        originalLocation[1] = locations[1] - parentLocations[1];
        dropText.setX(originalLocation[0]);
        dropText.setY(originalLocation[1]);
    }
    
    public int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }
}
