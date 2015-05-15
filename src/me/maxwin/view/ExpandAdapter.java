package me.maxwin.view;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExpandAdapter extends BaseExpandableListAdapter{
    private ArrayList<String> p ;
    private ArrayList<String> son;
    private Context ctx;
	public ExpandAdapter (ArrayList<String>p,ArrayList<String> son,Context ctx){
		this.p = p;
		this.son = son;
		this.ctx = ctx;
	}
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return son.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return  p.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return p.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return son.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		LinearLayout ly = new LinearLayout(ctx);
		TextView txt = new TextView(ctx);
		txt.setText(p.get(groupPosition));
		ly.addView(txt);
		return ly;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		LinearLayout ly = new LinearLayout(ctx);
		TextView txt = new TextView(ctx);
		txt.setText(son.get(childPosition));
		ly.addView(txt);
		return ly;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

}
