package com.devstorm.growingtools.adapterss;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.devstorm.growingtools.R;
import com.devstorm.growingtools.models.SlidingMenuModel;

import java.util.List;

public class SlidingMenuAdapter  extends BaseAdapter {

    private List<SlidingMenuModel> list;
    private Context context;
    private int resource;

    public SlidingMenuAdapter(List<SlidingMenuModel> list, Context context, int resource) {
        this.list = list;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return  list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context,resource,null);
        TextView tv = v.findViewById(R.id.textviewsliding);
        ImageView im = v.findViewById(R.id.imageviewsliding);
        SlidingMenuModel model = list.get(position);
        tv.setText(model.getTitle());
        im.setImageResource(model.getImageID());
        return v;
    }
}
