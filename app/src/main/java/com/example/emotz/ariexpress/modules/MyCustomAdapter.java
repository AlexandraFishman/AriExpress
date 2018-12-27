package com.example.emotz.ariexpress.modules;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.emotz.ariexpress.R;

import java.util.ArrayList;

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;
    public MyCustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {

        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        notifyDataSetChanged();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cortume_layout_list, null);
        }

        //Handle TextView and display string from your list
        //here we take a single item and print it to the list, I took only name
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position).split("\n")[0]);

        //Handle buttons and add onClickListeners
        Button cartBtn = (Button)view.findViewById(R.id.cart_btn);

        cartBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                Log.d("?!$?!?!arbel$?!?!$?!?= ", list.get(position).toString()+" "+ position);
                //parent.getChildAt(0).getId();
                //Log.d("out: ", );
                notifyDataSetChanged();
            }
        });

        notifyDataSetChanged();
        return view;
    }
}