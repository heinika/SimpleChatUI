package com.lingzhuo.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.lingzhuo.viewpager.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private ViewPager mViewPager;
    private List<View> views;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.view1,null);
        View view2 = inflater.inflate(R.layout.view2,null);
        View view3 = inflater.inflate(R.layout.view3,null);
        views = new ArrayList<>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        ViewPagerAdapter adapter = new ViewPagerAdapter(views);
        mViewPager.setAdapter(adapter);
    }

}
