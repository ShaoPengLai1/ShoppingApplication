package com.bwei.shoppingcar;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import adapter.MyFragAdapter;
import fragment.FirstFragment;
import fragment.MyFragment;

public class ShowActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        initViews();
        List<Fragment> list=new ArrayList<>();
        list.add(new FirstFragment());
        list.add(new MyFragment());
        MyFragAdapter fragAdapter=new MyFragAdapter(getSupportFragmentManager(),list);
        viewPager.setAdapter(fragAdapter);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.rb2:
                        viewPager.setCurrentItem(1);
                        break;
                }
            }
        });
    }

    private void initViews() {
        viewPager = findViewById(R.id.viewpager);
        radioGroup = findViewById(R.id.radio_group);
    }
}
