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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kongzue.classseatchangedemo.util.SScrollView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    public final int TABLETITLECOLOR = Color.argb(50, 216, 28, 96);
    public final int FOCUSCOLOR = Color.argb(100, 0, 133, 120);
    public final int EMPTYCOLOR = Color.argb(30, 0, 0, 0);
    
    private SScrollView scrollView;
    private LinearLayout boxHorizontalTabTitlePadding;
    private LinearLayout boxVerticalTabTitlePadding;
    private GridLayout gridLayout;
    private TextView dropText;
    private LinearLayout boxHorizontalTabTitle;
    private LinearLayout boxVerticalTabTitle;
    
    private List<View> views;
    private List<String> datas;
    
    private View onDropView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //绑定布局
        scrollView = findViewById(R.id.scrollView);
        boxHorizontalTabTitlePadding = findViewById(R.id.box_horizontal_tabTitlePadding);
        boxVerticalTabTitlePadding = findViewById(R.id.box_vertical_tabTitlePadding);
        gridLayout = findViewById(R.id.gridLayout);
        dropText = findViewById(R.id.drop_text);
        boxHorizontalTabTitle = findViewById(R.id.box_horizontal_tabTitle);
        boxVerticalTabTitle = findViewById(R.id.box_vertical_tabTitle);
        
        initDemoDatas();
        initTableTitle();
        initTables();
    }
    
    private void initTableTitle() {
        View itemPadding = LayoutInflater.from(this).inflate(R.layout.layout_item, null, false);
        boxHorizontalTabTitlePadding.addView(itemPadding);
        itemPadding = LayoutInflater.from(this).inflate(R.layout.layout_item, null, false);
        boxVerticalTabTitlePadding.addView(itemPadding);
        for (int i = 0; i < 21; i++) {
            View item = LayoutInflater.from(this).inflate(R.layout.layout_item, null, false);
            TextView itemText = item.findViewById(R.id.item_text);
            itemText.setText(i + "");
            itemText.setBackgroundColor(TABLETITLECOLOR);
            if (i == 0) itemText.setVisibility(View.INVISIBLE);
            boxHorizontalTabTitle.addView(item);
        }
        for (int i = 0; i < 21; i++) {
            View item = LayoutInflater.from(this).inflate(R.layout.layout_item, null, false);
            TextView itemText = item.findViewById(R.id.item_text);
            itemText.setText((i) + "");
            itemText.setBackgroundColor(TABLETITLECOLOR);
            boxVerticalTabTitle.addView(item);
        }
    
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                boxVerticalTabTitle.setY(-scrollView.getScrollY());
                boxHorizontalTabTitle.setX(-scrollView.getScrollX());
            }
        });
    }
    
    private void initDemoDatas() {
        datas = new ArrayList<>();
        datas.add("赵钱");
        datas.add("冯陈");
        datas.add("朱秦");
        datas.add("孔曹");
        datas.add("戚谢");
        datas.add("云苏");
        datas.add("鲁韦");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("俞任");
        datas.add("费廉");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("滕殷");
        datas.add("乐于");
        datas.add("伍余");
        datas.add("和穆");
        datas.add("祁毛");
        datas.add("计伏");
        datas.add("熊纪");
        datas.add("杜阮");
        datas.add("贾路");
        datas.add("梅盛");
        datas.add("高夏");
        datas.add("虞万");
        datas.add("经房");
        datas.add("丁宣");
        datas.add("包诸");
        datas.add("孙李");
        datas.add("褚卫");
        datas.add("尤许");
        datas.add("严华");
        datas.add("邹喻");
        datas.add("潘葛");
        datas.add("昌马");
        datas.add("袁柳");
        datas.add("岑薛");
        datas.add("罗毕");
        datas.add("时傅");
        datas.add("元卜");
        datas.add("萧尹");
        datas.add("禹狄");
        datas.add("成戴");
        datas.add("舒屈");
        datas.add("蓝闵");
        datas.add("娄危");
        datas.add("林刁");
        datas.add("蔡田");
        datas.add("支柯");
        datas.add("裘缪");
        datas.add("贲邓");
        datas.add("左石");
        
        if (datas.size() < 20 * 20) {
            for (int i = datas.size(); i < 20 * 20; i++) {
                datas.add("");
            }
        }
    }
    
    //绘制表格+处理事务
    private void initTables() {
        views = new ArrayList<>();
        for (int i = 0; i < 20 * 20; i++) {
            final View item = LayoutInflater.from(this).inflate(R.layout.layout_item, null, false);
            TextView itemText = item.findViewById(R.id.item_text);
            itemText.setTag(i);
            
            if (i < datas.size() && !datas.get(i).isEmpty()) {
                itemText.setText(datas.get(i) + "(" + getLocPoint(i) + ")");
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
                        
                        dropText.setX(locations[0] + scrollView.getScrollX()+ dropText.getWidth() );
                        dropText.setY(locations[1] + scrollView.getScrollY()+ dropText.getHeight());
                        return false;
                    }
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        if (onDropView != null) {
                            if (originalLocation == null) originalLocation = new int[2];
                            dropText.setX(originalLocation[0] + event.getX() - touchDownLocation[0] - scrollView.getScrollX() + dropText.getWidth() );
                            dropText.setY(originalLocation[1] + event.getY() - touchDownLocation[1] - scrollView.getScrollY() + dropText.getHeight());
                            
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
                            centerPoint[0] = (int) (dropText.getX()-dropText.getWidth()  + dropText.getWidth() / 2) + scrollView.getScrollX();
                            centerPoint[1] = (int) (dropText.getY()-dropText.getHeight() + dropText.getHeight() / 2) + scrollView.getScrollY();
                            
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
    
                                        TextView oldTextView = onDropView.findViewById(R.id.item_text);
                                        int oldIndex= (int) oldTextView.getTag();
                                        datas.set(i, datas.get(oldIndex));
                                        datas.set(oldIndex, "");
                                        
                                        TextView itemText = item.findViewById(R.id.item_text);
                                        if (itemText.getText().toString().isEmpty()) {
                                            itemText.setText(datas.get(i) + "(" + getLocPoint(i) + ")");
                                            itemText.setBackgroundColor(FOCUSCOLOR);
                                            
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
        dropText.setX(originalLocation[0]+ dropText.getWidth() );
        dropText.setY(originalLocation[1]+ dropText.getHeight());
    }
    
    public int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }
}
