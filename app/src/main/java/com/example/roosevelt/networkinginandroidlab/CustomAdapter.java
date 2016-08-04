package com.example.roosevelt.networkinginandroidlab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.List;

/**
 * Created by roosevelt on 8/4/16.
 */
public class CustomAdapter extends BaseAdapter {

    List<GroceryItem> itemList;
    Context context;

    public CustomAdapter(Context context, List<GroceryItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View twoLineListItem;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            twoLineListItem = inflater.inflate(
                    android.R.layout.simple_list_item_2, null);
        } else {
            twoLineListItem = view;
        }

        TextView text1 = (TextView) twoLineListItem.findViewById(android.R.id.text1);
        TextView text2 = (TextView) twoLineListItem.findViewById(android.R.id.text2);

        text1.setText(itemList.get(i).getName());
        text2.setText("$ " + itemList.get(i).getPrice());

        return twoLineListItem;
    }
}
