package com.example.peng.shoppingapplication;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class CustomJiaJian extends LinearLayout {
    private Button reverse;
    private Button add;
    private EditText countEdit;
    private int mCount=1;


    public CustomJiaJian(Context context) {
        super(context);
    }

    public CustomJiaJian(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view=View.inflate(context,R.layout.custom_jiajian,this);
        reverse=view.findViewById(R.id.reverse);
        add=view.findViewById(R.id.add);
        countEdit=view.findViewById(R.id.count);

        reverse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = countEdit.getText().toString().trim();
                Integer count = Integer.valueOf(content);
                if (count>1){
                    mCount=count-1;
                    countEdit.setText(mCount+"");
                    if (customListener!=null){
                        customListener.jiajian(mCount);
                    }
                }
            }
        });
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = countEdit.getText().toString().trim();
                Integer count = Integer.valueOf(content);
                mCount=count;
                countEdit.setText(mCount+"");
                if (customListener!=null){
                    customListener.jiajian(mCount);
                }
            }
        });
    }

    public CustomJiaJian(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    CustomListener customListener;
    public void setCustomListener(CustomListener customListener){
        this.customListener=customListener;
    }
    public interface CustomListener{
        void jiajian(int count);
        void shuRuZhi(int count);
    }
    public void setEditText(int num){
        if (countEdit!=null){
            countEdit.setText(num+"");
        }
    }
}
