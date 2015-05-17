package me.maxwin.view;

import me.maxwin.R;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ExpandHeaderView extends LinearLayout {
	private ProgressBar bar;
	private TextView txt_pull;
	private TextView txt_time;
	private ImageView image_pull;
	private LinearLayout content;
	private int mState = STATE_NORMAL;

	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public  final static int STATE_REFESHING = 2;

	private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;
	private final int ROTATE_ANIM_DURATION = 180;

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
		setOrientation(VERTICAL);
		FrameLayout ly = new FrameLayout(ctx);
		ly.setPadding(0, 10, 0, 0);
		content = new LinearLayout(ctx);
		content.setGravity(Gravity.BOTTOM);
		LayoutParams l_ly = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		l_ly.gravity = Gravity.BOTTOM;
		addView(ly,l_ly);
		
		FrameLayout.LayoutParams lp_content = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				0,Gravity.BOTTOM);
		setBackgroundColor(Color.WHITE);
		content.setOrientation(HORIZONTAL);
		ly.addView(content, lp_content);
		FrameLayout left = new FrameLayout(ctx);
		//left.setPadding(10, 10, 10, 10);
		LayoutParams lp_left = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		lp_left.height = 100;
		lp_left.weight = 0.6f;
		content.addView(left, lp_left);
		image_pull = new ImageView(ctx);
		image_pull.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.xlistview_arrow));
		FrameLayout.LayoutParams lp_image = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp_image.gravity =  Gravity.CENTER;
		lp_image.topMargin = 10;
		left.addView(image_pull, lp_image);

		bar = new ProgressBar(ctx);
		bar.setVisibility(View.GONE);
		left.addView(bar, lp_image);

		LinearLayout right = new LinearLayout(ctx);
		right.setOrientation(VERTICAL);
		right.setGravity(Gravity.BOTTOM);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		lp.gravity = Gravity.LEFT;
		lp.weight = 0.4f;
		lp.leftMargin = 10;
		content.addView(right, lp);

		txt_pull = new TextView(ctx);
		txt_pull.setText("下拉刷新");
		right.addView(txt_pull);

		txt_time = new TextView(ctx);
		txt_time.setText("最近刷新时间:");
		right.addView(txt_time);

		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);

		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);

	}

	public LinearLayout getContent() {
		return content;
	}

	public void setSate(int state) {
		if (state == mState)
			return;

		if (state == STATE_REFESHING) {
			image_pull.clearAnimation();
			image_pull.setVisibility(View.INVISIBLE);
			bar.setVisibility(View.VISIBLE);
		} else {
			image_pull.setVisibility(View.VISIBLE);
			bar.setVisibility(View.INVISIBLE);

		}

		switch (state) {
		case STATE_NORMAL:
			if (mState == STATE_READY) {
				image_pull.startAnimation(mRotateDownAnim);
			}
			if (mState == STATE_REFESHING) {

				image_pull.clearAnimation();
			}
			txt_pull.setText("下拉刷新");
			break;

		case STATE_READY:
			if (mState != STATE_READY) {
				image_pull.clearAnimation();
				image_pull.startAnimation(mRotateUpAnim);
				txt_pull.setText("松开刷新数据");
			}
			break;

		case STATE_REFESHING:
			txt_pull.setText("正在加载..");
			break;
		default:
		}
		mState = state;
	}

	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) content
				.getLayoutParams();
		lp.height = height;
		System.out.println("height ==== "+height);
		content.setLayoutParams(lp);

	}

	public int getVisiableHeight() {
		return content.getLayoutParams().height;
	}

	public void updateTime(String time) {
		if (TextUtils.isEmpty(time)) {
			txt_time.setText("最近刷新时间为刚刚");
		} else {
			txt_time.setText("最近刷新时间:" + time);

		}
	}
}
