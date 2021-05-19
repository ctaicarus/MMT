package com.example.demomess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Mess  {
    private MessActivity context;
    private int layout;
    private List<String> mess;

    public Mess(MessActivity context , int layout , List<String> mess) {
        this.context = context;
        this.layout = layout;
        this.mess = mess;
    }



    private class ViewHolder{
        TextView textView;
        ImageView imgView;
    }


    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout , null);
            holder.textView = view.findViewById(R.id.person_ib);
            holder.imgView = view.findViewById(R.id.person);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        return view;
    }

}
