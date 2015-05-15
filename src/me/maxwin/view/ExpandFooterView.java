package me.maxwin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ExpandFooterView extends LinearLayout{
    private ProgressBar bar;
    private TextView txt;
	public ExpandFooterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public ExpandFooterView(Context context) {
		super(context);
		initView(context);                                  
	}


	private void initView(Context ctx){
		FrameLayout frame = new FrameLayout(ctx);
		addView(frame,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		bar = new ProgressBar(ctx);
		frame.addView(bar,new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,Gravity.CENTER));
        txt = new TextView(ctx);
        txt.setText("查看更多");
        frame.addView(txt,new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,Gravity.CENTER));
	}
   
	
	
}
