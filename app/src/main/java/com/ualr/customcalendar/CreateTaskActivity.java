package com.ualr.customcalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.ualr.customcalendar.databinding.ActivityCreatetaskBinding;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity implements DatePickerDialogFragment.NoticeDialogListener,TimePickerDialogFragment.NoticeDialogListener{

    private static final String FRAGMENT_TAG = "SaveDialog";


    private ActivityCreatetaskBinding mBinding;
    private static final String TAG = CreateTaskActivity.class.getSimpleName();
    private TextView dateTV, timeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtask);
        dateTV = findViewById(R.id.date_view);
        timeTV = findViewById(R.id.time_view);
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
    public void onDialogDateClick(String date){
        this.dateTV.setText(date);
    }

    @Override
    public void onDialogTimeClick(String time){
        this.timeTV.setText(time);
    }

}