package com.example.sqlite02.ListAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sqlite02.R;

import java.util.List;

public class ListAdapterClass extends BaseAdapter {
    Context context;
    List<subjects> valueList;
    public ListAdapterClass(List<subjects> listValue, Context context)
    {
        this.context = context;
        this.valueList = listValue;
    }
    @Override
    public int getCount()
    {
        return this.valueList.size();
    }
    @Override
    public Object getItem(int position)
    {
        return this.valueList.get(position);
    }
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewItem viewItem = null;
        if(convertView == null)
        {
            viewItem = new ViewItem();
            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInfiater.inflate(R.layout.layout_items, null);
            viewItem.vFname = (TextView)convertView.findViewById(R.id.tvfname);
            viewItem.vLname = (TextView)convertView.findViewById(R.id.tvlname);
            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem) convertView.getTag();
        }
        viewItem.vFname.setText(valueList.get(position).sFname);
        viewItem.vLname.setText(valueList.get(position).sLname);
        return convertView;
    }
}

class ViewItem
{
    TextView vFname,vLname;
}

