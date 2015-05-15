package me.maxwin.view;

import android.content.Context;
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
	private IExpandablePullListener expandListener;
	private Scroller mScroller;
	private ExpandHeaderView headerview;
	private ExpandFooterView footerview;
	private int mHeaderViewHeight;
	private LinearLayout mHeaderContent;
	private boolean mIsFooterRead =false;;
	private boolean mEnablePullRefresh;
	public ExpandPullListView(Context context) {
		super(context);
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
						mHeaderViewHeight = mHeaderContent.getHeight();
						getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
					}
				});

	}
    @Override
    public void setAdapter(ExpandableListAdapter adapter) {
        if(mIsFooterRead==false){
         mIsFooterRead = true;
        }
    	super.setAdapter(adapter);
    }
    
    public void setPullRefreshEnable(boolean enable){
    	mEnablePullRefresh = enable;
    	if(!mEnablePullRefresh){
    		mHeaderContent.setVisibility(View.INVISIBLE);
    	}else{
    		mHeaderContent.setVisibility(View.VISIBLE);
    	}
    }
    
    public void setPullLoadEnable(boolean enable){
    	
    	
    	
    }
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

	public void setExpandableListener(IExpandablePullListener expandListener) {

		this.expandListener = expandListener;
	}

	interface OnExpandSrollListener extends OnScrollListener {
		public void onExpandScrolling(View view);
	}

	interface IExpandablePullListener {
		public void onRefresh();

		public void onLoadMore();

	}

}
