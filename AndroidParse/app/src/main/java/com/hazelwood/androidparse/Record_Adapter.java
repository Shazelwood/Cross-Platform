package com.hazelwood.androidparse;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hazelwood on 2/5/15.
 */
public class Record_Adapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Record> mObjects;

    private static final long ID_CONSTANT = 123456789;

    public Record_Adapter(Context c, ArrayList<Record> objects){
        mContext = c;
        mObjects = objects;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public Record getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ID_CONSTANT + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }

        Record item = getItem(position);

        TextView title = (TextView)convertView.findViewById(R.id.item_title);
        TextView description = (TextView)convertView.findViewById(R.id.item_description);
        TextView completion = (TextView)convertView.findViewById(R.id.item_complete);

        boolean complete = item.recordComplete;

        if (complete){
            completion.setText("COMPLETE");
            completion.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            completion.setText("NOT COMPLETE");
            completion.setTextColor(Color.parseColor("#F44336"));
        }

        title.setText(item.getRecordTitle());
        description.setText(item.getRecordDescription());


        return convertView;
    }
}
