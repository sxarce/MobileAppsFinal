package com.ualr.customcalendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {

    Activity mActivity;
    ArrayList<Task> mTasks;

    public TaskAdapter(Activity mActivity, ArrayList<Task> mTasks){
        this.mActivity = mActivity;
        this.mTasks = mTasks;
    }

    @Override
    public int getCount() {
        return mTasks.size();
    }

    @Override
    public Task getItem(int position) {
        return mTasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View taskLine;
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        taskLine = inflater.inflate(R.layout.task_layout, parent, false);

        LinearLayout task_view = taskLine.findViewById(R.id.task_view);
        TextView tv_hour = taskLine.findViewById(R.id.tv_hour);
        TextView tv_min = taskLine.findViewById(R.id.tv_min);
        TextView tv_timetype = taskLine.findViewById(R.id.tv_timetype);
        TextView tv_title = taskLine.findViewById(R.id.tv_title);
        TextView tv_description = taskLine.findViewById(R.id.tv_description);



        Task t = this.getItem(position);
        String hour = String.format("%02d", t.getHour());
        String min = String.format("%02d", t.getMin());


        tv_hour.setText(hour);
        tv_min.setText(min);
        tv_timetype.setText(t.getTimeType());
        tv_title.setText(t.getTitle());
        tv_description.setText(t.getDescription());

        int priority = t.getPriority();
        if(priority == 0){
            task_view.setBackgroundColor(Color.parseColor("#94ffab"));
        }else if(priority == 1){
            task_view.setBackgroundColor(Color.parseColor("#ffff91"));
        }else{
            task_view.setBackgroundColor(Color.parseColor("#ffa294"));
        }

        return taskLine;
    }

}
