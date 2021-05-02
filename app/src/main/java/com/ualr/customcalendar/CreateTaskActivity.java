package com.ualr.customcalendar;

import androidx.appcompat.app.AppCompatActivity;

import com.ualr.customcalendar.databinding.ActivityCreatetaskBinding;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CreateTaskActivity extends AppCompatActivity implements DatePickerDialogFragment.NoticeDialogListener,TimePickerDialogFragment.NoticeDialogListener{

    private static final String FRAGMENT_TAG = "SaveDialog";
    int nDay, nMonth, nYear, nMin, nHour;
    String nTimeType;

    private ActivityCreatetaskBinding mBinding;
    private static final String TAG = CreateTaskActivity.class.getSimpleName();
    private TextView dateTV, timeTV;
    private EditText titleET, priorityET, descriptionET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtask);
        dateTV = findViewById(R.id.date_view);
        timeTV = findViewById(R.id.time_view);
        titleET = findViewById(R.id.TaskTitle);
        priorityET = findViewById(R.id.Priority);
        descriptionET = findViewById(R.id.TaskDescription);
    }

    public void showTimePickerDialog(View view) {
        TimePickerDialogFragment dialog = new TimePickerDialogFragment();
        dialog.show(getSupportFragmentManager(), FRAGMENT_TAG);
    }

    public void showDatePickerDialog(View view) {
        DatePickerDialogFragment dialog = new DatePickerDialogFragment();
        dialog.show(getSupportFragmentManager(), FRAGMENT_TAG);
    }

    @Override
    public void onDialogDateClick(int month, int day, int year, String date){
        nMonth = month;
        nDay = day;
        nYear = year;
        this.dateTV.setText(date);
    }

    @Override
    public void onDialogTimeClick(int hour, int min, String type, String time){
        nHour = hour;
        nMin = min;
        nTimeType = type;
        this.timeTV.setText(time);
    }

    public void onSaveTaskButtonClick(View view){
        String task = this.titleET.getText().toString();
        String task_priority = this.priorityET.getText().toString();
        String task_description = this.descriptionET.getText().toString();
    }
}