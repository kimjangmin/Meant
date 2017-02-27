package com.mean.demo.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mean.demo.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CBR on 2016-01-20.
 */
public class Adp extends BaseExpandableListAdapter
{
    private Context context;
    private ArrayList<String> arrayGroup;
    private HashMap<String , ArrayList<String>> arraytChild;

    public Adp(Context context,ArrayList<String> arrayGroup,HashMap<String,ArrayList<String>> arraytChild)
    {
        super();
        this.context = context;
        this.arrayGroup = arrayGroup;
        this.arraytChild =arraytChild;
    }
    @Override
    public int getGroupCount() {
        return arrayGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return arraytChild.get(arrayGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return arrayGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return arraytChild.get(arrayGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String groupName = arrayGroup.get(groupPosition);
        View v = convertView;

        if(v==null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v = (RelativeLayout)inflater.inflate(R.layout.adp_group,null);
        }
        TextView text = (TextView)v.findViewById(R.id.group);
        text.setText(groupName);
        text.setTextColor(context.getResources().getColor(R.color.monsoon));

        return v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String groupName2 = arraytChild.get(arrayGroup.get(groupPosition)).get(childPosition);
        View v = convertView;

        if(v==null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v = (RelativeLayout)inflater.inflate(R.layout.adp_child,null);
        }
        TextView text = (TextView)v.findViewById(R.id.child);
        text.setText(groupName2);
        text.setTextColor(context.getResources().getColor(R.color.monsoon));
        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}
