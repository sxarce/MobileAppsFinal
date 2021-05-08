package com.ualr.customcalendar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ualr.customcalendar.databinding.ActivityDailytaskviewBinding;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class DailyTaskViewActivity extends AppCompatActivity implements DatePickerDialogFragment.NoticeDialogListener {

    public static final String EXTRA_CONTACT = "Database";
    static final int TASK_CODE_REQUEST = 1;
    static final int TASK_UPDATE_CODE_REQUEST = 2;
    static final String TASK_KEY = "TaskCode";
    private static final String TAG = "DailyTaskView";
    DatabaseHelper mDatabaseHelper;
    TaskAdapter adapter;
    TextView datetv;
    Calendar c;
    private ActivityDailytaskviewBinding mBinding;
    private ListView mListView;
    private int cMonth;
    private int cDay;
    private int cYear;
    private int cDayWeek;
    private String cMonthText;
    private String dayPost;
    private String dayOfWeek;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailytaskview);
        mDatabaseHelper = new DatabaseHelper(this);
        mListView = findViewById(R.id.ListView);
        datetv = findViewById(R.id.dateTV);
        c = Calendar.getInstance();
        c.getTime();

        getAndSetDate();
        populateListViewFromDate(cMonth, cDay, cYear);
    }

    private void getAndSetDate() {
        cMonth = c.get(Calendar.MONTH) + 1;
        cDay = c.get(Calendar.DAY_OF_MONTH);
        cYear = c.get(Calendar.YEAR);
        cMonthText = getMonth(cMonth);
        cDayWeek = c.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = getDayOfWeek(cDayWeek);
        if(cDay == 1) dayPost = "st";
        else if(cDay == 2) dayPost = "nd";
        else if (cDay == 3) dayPost = "rd";
        else dayPost = "th";

        datetv.setText(String.format(dayOfWeek+", "+cMonthText+ " %d"+dayPost+", %d", cDay, cYear));
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the list view");
        Cursor data = mDatabaseHelper.getData();
        ArrayList<Task> listData = new ArrayList<>();
        while (data.moveToNext()) {
            Task task = new Task();
            task.setYear(data.getInt(data.getColumnIndex("year")));
            task.setDay(data.getInt(data.getColumnIndex("day")));
            task.setMonth(data.getInt(data.getColumnIndex("month")));
            task.setHour(data.getInt(data.getColumnIndex("hour")));
            task.setMin(data.getInt(data.getColumnIndex("min")));
            task.setTimeType(data.getString(data.getColumnIndex("time_type")));
            task.setTitle(data.getString(data.getColumnIndex("task_title")));
            task.setPriority(data.getInt(data.getColumnIndex("task_priority")));
            task.setDescription(data.getString(data.getColumnIndex("task_description")));
            listData.add(task);
        }
        adapter = new TaskAdapter(this, listData);
        mListView.setAdapter(adapter);
    }

    private void populateListViewFromDate(int month, int day, int year) {
        Log.d(TAG, "populateListView: Displaying data in the list view");
        Cursor data = mDatabaseHelper.getDataForDate(month, day, year);
        ArrayList<Task> listData = new ArrayList<>();
        while (data.moveToNext()) {
            Task task = new Task();
            task.setYear(data.getInt(data.getColumnIndex("year")));
            task.setDay(data.getInt(data.getColumnIndex("day")));
            task.setMonth(data.getInt(data.getColumnIndex("month")));
            task.setHour(data.getInt(data.getColumnIndex("hour")));
            task.setMin(data.getInt(data.getColumnIndex("min")));
            task.setTimeType(data.getString(data.getColumnIndex("time_type")));
            task.setTitle(data.getString(data.getColumnIndex("task_title")));
            task.setPriority(data.getInt(data.getColumnIndex("task_priority")));
            task.setDescription(data.getString(data.getColumnIndex("task_description")));
            listData.add(task);
        }
        adapter = new TaskAdapter(this, listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = (Task) adapterView.getItemAtPosition(i);
                Log.d(TAG, "onItemClick: You Clicked on " + task.getTitle());

                Cursor data = mDatabaseHelper.getItemID(task.getTitle(), task.getDescription());
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: the ID is: " + itemID);
                    Intent editTaskIntent = new Intent(DailyTaskViewActivity.this, EditDataActivity.class);
                    editTaskIntent.putExtra("id", itemID);
                    editTaskIntent.putExtra("task", task);
                    startActivityForResult(editTaskIntent, TASK_UPDATE_CODE_REQUEST);
                }else{
                    toastMessage("No ID associated with that task");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_action) {
            Intent intent = new Intent(this, CreateTaskActivity.class);
            startActivityForResult(intent, TASK_CODE_REQUEST);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, String.format("requestCode: %s, resultCode: %s", requestCode, resultCode));
        if (requestCode == TASK_CODE_REQUEST && resultCode == RESULT_OK) {
            Task newTask = data.getParcelableExtra(TASK_KEY);
            int thisMonth = newTask.getMonth();
            int thisDay = newTask.getDay();

            AddData(newTask.getYear(), newTask.getMonth(), newTask.getDay(), newTask.getHour(),
                    newTask.getMin(), newTask.getTimeType(), newTask.getTitle(), newTask.getPriority(), newTask.getDescription());

            populateListViewFromDate(cMonth, cDay, cYear);
        }
        else if(requestCode == TASK_UPDATE_CODE_REQUEST && resultCode == RESULT_OK){
            populateListViewFromDate(cMonth, cDay, cYear);
        }

    }

    public void AddData(int year, int month, int day, int hour, int min, String time_type,
                        String title, int priority, String description) {
        boolean insertData = mDatabaseHelper.addData(year, month, day, hour, min, time_type,
                title, priority, description);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }


    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onFab(View view) {
        DatePickerDialogFragment dialog = new DatePickerDialogFragment();
        dialog.show(getSupportFragmentManager(), TAG);
    }

    @Override
    public void onDialogDateClick(int month, int day, int year, String date) {
        cMonth = month;
        cDay = day;
        cYear = year;
        c.set(year, month - 1, day);
        getAndSetDate();
        populateListViewFromDate(cMonth, cDay, cYear);
    }

    public String getMonth(int month){
        switch (month){
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "something messed up";
        }
    }

    public String getDayOfWeek(int dayWeek){
        if(dayWeek == c.SUNDAY) return "Sunday";
        else if(dayWeek == c.MONDAY) return "Monday";
        else if(dayWeek == c.TUESDAY) return "Tuesday";
        else if(dayWeek == c.WEDNESDAY) return "Wednesday";
        else if(dayWeek == c.THURSDAY) return "Thursday";
        else if(dayWeek == c.FRIDAY) return "Friday";
        else return "Saturday";
    }
}
