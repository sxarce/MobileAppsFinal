package com.ualr.customcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.ualr.customcalendar.databinding.ActivityCreatetaskBinding;

public class CreateTaskActivity extends AppCompatActivity implements DatePickerDialogFragment.NoticeDialogListener, TimePickerDialogFragment.NoticeDialogListener {

    private static final String FRAGMENT_TAG = "SaveDialog";
    private static final String TAG = CreateTaskActivity.class.getSimpleName();
    public int nDay, nMonth, nYear, nMin, nHour;
    public String nTimeType;
    DatabaseHelper mDatabaseHelper;
    private ActivityCreatetaskBinding mBinding;
    private TextView dateTV, timeTV;
    private EditText titleET, descriptionET;
    private Task newTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtask);
        dateTV = findViewById(R.id.date_view);
        timeTV = findViewById(R.id.time_view);
        titleET = findViewById(R.id.TaskTitle);
        descriptionET = findViewById(R.id.TaskDescription);
        mDatabaseHelper = getIntent().getParcelableExtra(DailyTaskViewActivity.EXTRA_CONTACT);
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
    public void onDialogDateClick(int month, int day, int year, String date) {
        nMonth = month;
        nDay = day;
        nYear = year;
        this.dateTV.setText(date);
    }

    @Override
    public void onDialogTimeClick(int hour, int min, String type, String time) {
        nHour = hour;
        nMin = min;
        nTimeType = type;
        this.timeTV.setText(time);
    }

    public void onSaveTaskButtonClick(View view) {
        String task = this.titleET.getText().toString();
        int task_priority = 0;
        String task_description = this.descriptionET.getText().toString();

        MaterialButton p_low, p_mid, p_high;
        p_low = findViewById(R.id.btn_low);
        p_mid = findViewById(R.id.btn_med);
        p_high = findViewById(R.id.btn_high);

        if (TextUtils.isEmpty(dateTV.getText())) {
            dateTV.setError("Please select a date");
            toastMessage("Please set a date");
        } else if (TextUtils.isEmpty(timeTV.getText())) {
            timeTV.setError("Please set a time");
            toastMessage("Please set a time");
        } else if (TextUtils.isEmpty(titleET.getText())) {
            titleET.setError("Please set a title");
            toastMessage("Please set a title");
        } else {
            if (p_low.isChecked()) task_priority = 0;
            else if (p_mid.isChecked()) task_priority = 1;
            else task_priority = 2;

            newTask = new Task(nYear, nMonth, nDay, nHour, nMin, nTimeType, task, task_priority, task_description);
            Intent intent = getIntent();
            intent.putExtra(DailyTaskViewActivity.TASK_KEY, newTask);
            setResult(DailyTaskViewActivity.RESULT_OK, intent);
            finish();
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}