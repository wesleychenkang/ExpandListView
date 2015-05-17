package me.maxwin.view;

import me.maxwin.view.XListView.OnXScrollListener;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class ExpandPullListView extends ExpandableListView implements
		OnScrollListener {
	
	
	private float mLastY = -1;
	private IExpandablePullListener expandListener;
	private Scroller mScroller;
	private OnScrollListener mScrollListener;
	
	// headerview;
	private ExpandHeaderView headerview;
	private LinearLayout mHeaderContent;
	private boolean mEnablePullRefresh=true;
	private boolean mPullRefreshing = false;
	private int mHeaderHeight;
	// footerview
	private ExpandFooterView footerview;
	private boolean mIsFooterRead = false;
	private boolean mEnablePullLoad;
	private boolean mPullLoading;

	private int mTotalItemCount;

	private int mScrollBack;

	private final static int SCROLLBACK_HEADER = 0;
	private final static int SCROLLBACK_FOOTER = 1;
	private final static int SCROLL_DURATION = 400;
	private final static int PULL_LAOD_MORE_DELTA = 50;

	private final static float OFFSET_RADIO = 1.8f;

	public ExpandPullListView(Context context) {
		super(context);
		initWithContext(context);
	}
	
	public ExpandPullListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWithContext(context);
  	}


	private void initWithContext(Context context) {
		mScroller = new Scroller(context, new DecelerateInterpolator());
		super.setOnScrollListener(this);
		setChildIndicator(null);
		setGroupIndicator(null);
		headerview = new ExpandHeaderView(context);
		addHeaderView(headerview);
		mHeaderContent = headerview.getContent();

		footerview = new ExpandFooterView(context);

		headerview.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						mHeaderHeight = mHeaderContent.getHeight();
						Log.d("test", "mHeaderContent.getHeight()"+mHeaderHeight);
						getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
					}
				});

	}

	@Override
	public void setAdapter(ExpandableListAdapter adapter) {
		if (mIsFooterRead == false) {
			mIsFooterRead = true;
			addFooterView(footerview);
		}
		super.setAdapter(adapter);
	}

	public void setPullRefreshEnable(boolean enable) {
		mEnablePullRefresh = enable;
		if (!mEnablePullRefresh) {
			mHeaderContent.setVisibility(View.INVISIBLE);
		} else {
			mHeaderContent.setVisibility(View.VISIBLE);
		}
	}

	public void setPullLoadEnable(boolean enable) {
		mEnablePullLoad = enable;
		if (!mEnablePullLoad) {
			footerview.sethide();
			footerview.setOnClickListener(null);
			setFooterDividersEnabled(false);
		} else {
			mPullLoading = false;
			footerview.show();
			footerview.setStatus(ExpandFooterView.STATE_NORMAL);
			setFooterDividersEnabled(true);
			footerview.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					startLoadMore();
				}
			});

		}

	}

	public void stopRefresh() {
		if (mPullRefreshing == true) {
			mPullRefreshing = false;
			resetHeaderHeight();
		}

	}

	public void stopLoadMore() {
		if (mPullLoading == true) {
			mPullLoading = false;
			footerview.setStatus(ExpandFooterView.STATE_NORMAL);
		}

	}

	public void setRefreshTime(String time) {

		headerview.updateTime(time);
	}

	private void invokeOnScrolling() {

		if (mScrollListener instanceof OnXScrollListener) {
			OnXScrollListener l = (OnXScrollListener) mScrollListener;
			l.onXScrolling(this);
		}
	}

	private void udateHeaderHeight(float delta) {
		headerview.setVisiableHeight((int) delta
				+ headerview.getVisiableHeight());
		System.out.println("headerview.getVisiableHeight()"+headerview.getVisiableHeight());
		if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
			if (headerview.getVisiableHeight() > mHeaderHeight) {
				headerview.setSate(ExpandHeaderView.STATE_READY);

			} else {
				headerview.setSate(ExpandHeaderView.STATE_NORMAL);

			}
			setSelection(0);
		}

	}

	private void resetHeaderHeight() {
		int height = headerview.getVisiableHeight();
		if (height == 0) {
			return;
		}
		if (mPullRefreshing && height <= mHeaderHeight) {
			return;
		}
		int finalHeight = 0;
		if (mPullRefreshing && height > mHeaderHeight) {
			finalHeight = mHeaderHeight;
		}
		mScrollBack = SCROLLBACK_HEADER;
		mScroller.startScroll(0, height, 0, finalHeight - height,
				SCROLL_DURATION);
		invalidate();

	}

	private void undateFooterHeight(float delat) {
		int height = footerview.getBottomMargin() + (int) delat;
		if (mEnablePullLoad && !mPullLoading) {
			if (height > PULL_LAOD_MORE_DELTA) {
				footerview.setStatus(ExpandFooterView.STATE_READY);
			} else {
				footerview.setStatus(ExpandFooterView.STATE_NORMAL);

			}
		}
		
		footerview.setBottomMargin(height);
		 
	}
	
	private void resetFooterHeight(){
		int bottomMargin = footerview.getBottomMargin();
		if(bottomMargin>0){
			mScrollBack = SCROLLBACK_FOOTER;
			mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,SCROLL_DURATION);
			invalidate();
		}
	}
  
	private void startLoadMore() {
		// 加载更多
		mPullLoading = true;
		footerview.setStatus(ExpandFooterView.STATE_LOADING);
		if(expandListener!=null){
			expandListener.onLoadMore();
		}

	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(mLastY==-1){
			mLastY = ev.getRawY();
		}
		switch(ev.getAction()){
		case MotionEvent.ACTION_DOWN:
			mLastY = ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float deltay = ev.getRawY()-mLastY;
			mLastY = ev.getRawY();
			if(getFirstVisiblePosition()==0 && (headerview.getVisiableHeight()>0 ||deltay>0)){
				udateHeaderHeight(deltay/OFFSET_RADIO);
				invokeOnScrolling();
			}else if(getLastVisiblePosition() == mTotalItemCount-1
					&& (footerview.getBottomMargin()>0 || deltay<0)){
				
				undateFooterHeight(-deltay/OFFSET_RADIO);
			}
			default:
				mLastY = -1; //reset;
				if(getFirstVisiblePosition()==0){
					 if(mEnablePullRefresh && headerview.getVisiableHeight()>mHeaderHeight){
						 mPullRefreshing = true;
						 headerview.setSate(ExpandHeaderView.STATE_REFESHING);
						 if(expandListener!=null){
							 expandListener.onRefresh();
						 }
					 }
					 resetHeaderHeight();
				}else if(getLastVisiblePosition()==mTotalItemCount-1){
					if(mEnablePullLoad &&
							footerview.getBottomMargin()>PULL_LAOD_MORE_DELTA
							&& !mPullLoading){
						startLoadMore();
					}
					resetFooterHeight();
				}
				
				
			break;
		}
		
		return super.onTouchEvent(ev);
	}
	
	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		
		if(mScroller.computeScrollOffset()){
			if(mScrollBack == SCROLLBACK_HEADER){
				headerview.setVisiableHeight(mScroller.getCurrY());
			}else{
				
				footerview.setBottomMargin(mScroller.getCurrY());
			}
			postInvalidate();
			invokeOnScrolling();
			
		}
		super.computeScroll();
	}
     @Override
    public void setOnScrollListener(OnScrollListener l) {
    	// TODO Auto-generated method stub
    	 mScrollListener = l;
    }	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
       if(mScrollListener!=null){
    	   
    	   mScrollListener.onScrollStateChanged(view, scrollState);
       }
	}

	

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
       mTotalItemCount = totalItemCount;
       if(mScrollListener!=null){
    	   mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
    	   
       }
	}

	public void setExpandableListener(IExpandablePullListener expandListener) {

		this.expandListener = expandListener;
	}

	public interface OnExpandSrollListener extends OnScrollListener {
		public void onExpandScrolling(View view);
	}

	public interface IExpandablePullListener {
		public void onRefresh();

		public void onLoadMore();

	}

}
