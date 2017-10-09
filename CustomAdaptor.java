package com.example.lenovo.my_todo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lenovo on 9/19/2017.
 */

class CustomAdaptor extends ArrayAdapter<Tododetail> {

    Context mcontext;
    ArrayList<Tododetail> mmain_list;
    DeleteButtonClickListner mDeleteButtonClickListner;


    public CustomAdaptor(@NonNull Context context, ArrayList<Tododetail> main_list, DeleteButtonClickListner DeleteButtonClickListner) {
        super(context, 0);
        mcontext = context;
        mmain_list = main_list;
        mDeleteButtonClickListner = DeleteButtonClickListner;
    }


    @Override
    public int getCount() {
        return mmain_list.size();
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Viewholder viewholder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.row_details, null);
            viewholder = new Viewholder();
            TextView task = (TextView) convertView.findViewById(R.id.TASK);
            TextView rank = (TextView) convertView.findViewById(R.id.RANK);
            Button delbutton = (Button) convertView.findViewById(R.id.delbutton);
            viewholder.task = task;
            viewholder.rank = rank;
            viewholder.delbutton = delbutton;
            convertView.setTag(viewholder);

        }

        viewholder = (Viewholder) convertView.getTag();
        viewholder.delbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDeleteButtonClickListner.onbuttonclick(position, view);
            }
        });


        Tododetail data = mmain_list.get(position);
        viewholder.task.setText(data.gettask());
        viewholder.rank.setText(data.getRank() + "");


        return convertView;
    }


    static class Viewholder {
        TextView task;
        TextView rank;
        Button delbutton;
    }


}
