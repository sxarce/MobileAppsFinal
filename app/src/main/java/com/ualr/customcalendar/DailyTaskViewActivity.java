package com.ualr.customcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ualr.customcalendar.databinding.ActivityDailytaskviewBinding;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DailyTaskViewActivity extends AppCompatActivity {

    private ActivityDailytaskviewBinding mBinding;
    public static final String EXTRA_CONTACT = "Database";
    DatabaseHelper mDatabaseHelper;

    static final int TASK_CODE_REQUEST = 1;
    static final String TASK_KEY = "TaskCode";

    private static final String TAG = "DailyTaskView";


    TextView dMonth, dDay, dYear, dTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailytaskview);
        mDatabaseHelper = new DatabaseHelper(this);

        dMonth = findViewById(R.id.display_month);
        dDay = findViewById(R.id.display_day);
        dYear = findViewById(R.id.display_year);
        dTitle = findViewById(R.id.display_title);
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
        Log.d(TAG, String.format("requestCode: %s, resultCode: %s",String.valueOf(requestCode), String.valueOf(resultCode)));
        if (requestCode == TASK_CODE_REQUEST && resultCode == RESULT_OK){
            Task newTask = data.getParcelableExtra(TASK_KEY);
            int thisMonth = newTask.getMonth();
            int thisDay = newTask.getDay();

            AddData(newTask.getYear(), newTask.getMonth(), newTask.getDay(), newTask.getHour(),
                    newTask.getMin(), newTask.getTimeType(), newTask.getTitle(), newTask.getPriority(), newTask.getDescription());
        };

    }

    public void AddData(int year, int month, int day, int hour, int min, String time_type,
                        String title, String priority, String description){
        boolean insertData = mDatabaseHelper.addData(year,month,day,hour,min,time_type,
                title,priority,description);

        if(insertData){
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }


    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
