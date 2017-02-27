package com.mean.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mean.demo.getset.Comment_value;
import com.mean.demo.all_listener.FireClickListener;
import com.mean.demo.R;

import java.util.ArrayList;

/**
 * Created by CBR on 2016-01-17.
 */
public class Comment_Adapter extends BaseAdapter {
    private ArrayList<Comment_value> m_List;
    public Comment_Adapter()
    {
        m_List = new ArrayList<Comment_value>();
    }

    @Override
    public int getCount() {
        return m_List.size();
    }

    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.comment_layout, parent, false);
            ImageView iv = (ImageView)convertView.findViewById(R.id.comore_iv);
            TextView t_id = (TextView)convertView.findViewById(R.id.comore_id);
            TextView t_msg = (TextView)convertView.findViewById(R.id.comore_msg);
            ImageView iv_fire = (ImageView)convertView.findViewById(R.id.com_fire);
            String str = m_List.get(position).getNick();
            t_id.setText(str);
            t_msg.setText(m_List.get(position).getMsg());
            if(m_List.get(position).getis_reple().equals("0"))
            {
                iv.setImageResource(R.drawable.comment);
            }
            else
            {
                iv.setImageResource(R.drawable.recomment);
            }
            iv_fire.setOnClickListener(new FireClickListener(context,Integer.parseInt(m_List.get(position).getcomment_num())));
        }
        return convertView;
    }

    public void add(Comment_value _msg)
    {
        m_List.add(_msg);
    }
    public void remove(int _position)
    {
        m_List.remove(_position);
    }
}
