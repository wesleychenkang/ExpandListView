package me.maxwin.view;

import me.maxwin.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ExpandHeaderView extends LinearLayout {
	private ProgressBar bar;
    private TextView txt_pull;
    private TextView txt_time;
    private ImageView image_pull;
    
	public ExpandHeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public ExpandHeaderView(Context context) {
		super(context);
		initView(context);
		// TODO Auto-generated constructor stub
	}

	private void initView(Context ctx) {
		LinearLayout all = new LinearLayout(ctx);
		LayoutParams lp_all = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		all.setOrientation(HORIZONTAL);
		addView(all,lp_all);
        
		FrameLayout left = new FrameLayout(ctx);
		LayoutParams lp_left = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		lp_left.weight = 0.6f;
		all.addView(left,lp_left);
		image_pull = new ImageView(ctx);
		image_pull.setBackgroundDrawable(getResources().getDrawable(R.drawable.xlistview_arrow));
	    FrameLayout.LayoutParams lp_image = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	    lp_image.gravity = Gravity.RIGHT|Gravity.CENTER;
		left.addView(image_pull,lp_image);
		
		bar = new ProgressBar(ctx);
		bar.setVisibility(View.VISIBLE);
		left.addView(bar,lp_image);
	
		
		LinearLayout right = new LinearLayout(ctx);
		right.setOrientation(VERTICAL);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.LEFT;
        lp.weight = 0.4f;
        lp.leftMargin = 10;
		all.addView(right,lp);
		
		txt_pull = new TextView(ctx);
		txt_pull.setText("下拉刷新");
		right.addView(txt_pull);
		
		txt_time = new TextView(ctx);
		txt_time.setText("最近刷新时间:");
		right.addView(txt_time);
	}

}
