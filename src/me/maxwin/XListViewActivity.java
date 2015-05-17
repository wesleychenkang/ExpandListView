package me.maxwin;

import java.util.ArrayList;

import me.maxwin.view.ExpandAdapter;
import me.maxwin.view.ExpandPullListView;
import me.maxwin.view.ExpandPullListView.IExpandablePullListener;
import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;

public class XListViewActivity extends Activity implements IXListViewListener {
	private XListView listview;
	private ExpandAdapter mAdapter;
	//private ExpandPullListView listview;
	private ArrayList<String> items = new ArrayList<String>();
	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		geneItems();
		listview = (XListView) findViewById(R.id.xListView);
	 //   listview = new ExpandPullListView(getApplicationContext());
//		listview.setPullLoadEnable(true);
		mAdapter = new ExpandAdapter(items,items,getApplicationContext());
		listview.setAdapter(mAdapter);
		listview.setPullLoadEnable(true);
     	listview.setPullRefreshEnable(true);
     	//listview.setExpandableListener(this);
    	listview.setXListViewListener(this);
	//	listview.setExpandableListener(this);
		mHandler = new Handler();
	}

	private void geneItems() {
		for (int i = 0; i != 40; ++i) {
			items.add("refresh cnt " + (++start));
		}
	}
  
	private void onLoad() {
		listview.stopRefresh();
		listview.stopLoadMore();
		listview.setRefreshTime("刚刚");
	}
	
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = ++refreshCnt;
				items.clear();
				geneItems();
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

}