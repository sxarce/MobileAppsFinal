package com.ualr.customcalendar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.ualr.customcalendar.databinding.ActivityCreatetaskBinding;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CreateTaskActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG = "SaveDialog";


    private ActivityCreatetaskBinding mBinding;
    private static final String TAG = CreateTaskActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtask);

    }

    public void showTimePickerDialog(View view) {
        TimePickerDialogFragment dialog = new TimePickerDialogFragment();
        dialog.show(getSupportFragmentManager(), FRAGMENT_TAG);
    }

    public void showDatePickerDialog(View view) {
        DatePickerDialogFragment dialog = new DatePickerDialogFragment();
        dialog.show(getSupportFragmentManager(), FRAGMENT_TAG);
    }

}