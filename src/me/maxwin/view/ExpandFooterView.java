package me.maxwin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ExpandFooterView extends LinearLayout {
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;
	private ProgressBar bar;
	private TextView txt;
	private LinearLayout content;

	public ExpandFooterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public ExpandFooterView(Context context) {
		super(context);
		initView(context);
	}

	public void sethide() {
		LayoutParams lp = (LayoutParams) content.getLayoutParams();
		lp.height = 0;
		content.setLayoutParams(lp);
	}

	private void initView(Context ctx) {
	    content = new LinearLayout(ctx);
		FrameLayout frame = new FrameLayout(ctx);
		frame.setPadding(10, 10, 10, 10);
		content.addView(frame,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		
		content.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		addView(content, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		bar = new ProgressBar(ctx);
		bar.setVisibility(View.GONE);
		frame.addView(bar, new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				Gravity.CENTER));
		txt = new TextView(ctx);
		txt.setText("查看更多");
		frame.addView(txt, new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				Gravity.CENTER));
	}

	public void show() {
		LayoutParams lp = (LayoutParams) content.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		content.setLayoutParams(lp);
	}

	/**
	 * 改变状态
	 * 
	 * @param status
	 */
	public void setStatus(int status) {
		 
		switch (status) {
		case STATE_NORMAL:
			//content.setVisibility(View.INVISIBLE);
			txt.setText("查看更多");
			txt.setVisibility(View.VISIBLE);
			bar.setVisibility(View.INVISIBLE);
			break;
		case STATE_READY:
			txt.setVisibility(View.VISIBLE);
			txt.setText("松开载入更多");
			bar.setVisibility(View.INVISIBLE);
			break;
		case STATE_LOADING:
			content.setVisibility(View.VISIBLE);
			txt.setVisibility(View.GONE);
			bar.setVisibility(View.VISIBLE);
			break;

		}

	}
	
	public int getBottomMargin(){
		LayoutParams lp = (LinearLayout.LayoutParams)content.getLayoutParams();
		Log.d("test", "lp.bottomMargin"+lp.bottomMargin);
		return lp.bottomMargin;
	}
	public void setBottomMargin(int height){
		Log.d("test", "setHeight"+height);
		if(height<0)
			return ;
		LayoutParams lp = (LinearLayout.LayoutParams)content.getLayoutParams();
		lp.bottomMargin = height;
		content.setLayoutParams(lp);
	}

}
