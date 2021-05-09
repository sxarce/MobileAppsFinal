package com.ualr.customcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class EditDataActivity extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

    private EditText new_title, new_description;
    private int priority;
    private MaterialButtonToggleGroup priority_selection_group;
    MaterialButton low, mid, high;

    DatabaseHelper mDatabaseHelper;

    private Task selectedTask;
    private int selectedID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_layout);
        new_title = findViewById(R.id.et_newTitle);
        new_description = findViewById(R.id.et_newDescription);
        mDatabaseHelper = new DatabaseHelper(this);
        priority_selection_group = findViewById(R.id.new_priority_button_group);
        low = findViewById(R.id.new_btn_low);
        mid = findViewById(R.id.new_btn_med);
        high = findViewById(R.id.new_btn_high);

        Intent recivedIntent = getIntent();
        selectedID = recivedIntent.getIntExtra("id", -1);
        selectedTask = recivedIntent.getParcelableExtra("task");

        new_title.setText(selectedTask.getTitle());
        new_description.setText(selectedTask.getDescription());
        priority = selectedTask.getPriority();

        if(priority == 0) priority_selection_group.check(R.id.new_btn_low);
        else if(priority == 1) priority_selection_group.check(R.id.new_btn_med);
        else if(priority == 2) priority_selection_group.check(R.id.new_btn_high);
    }

    public void onUpdatebtnClick(View view) {
        if (TextUtils.isEmpty(new_title.getText())){
            new_title.setError("Please enter a title");
        }else {
            String newTitle = new_title.getText().toString();
            String newDescription = new_description.getText().toString();
            int newPriority = 0;

            if (low.isChecked()) newPriority = 0;
            else if (mid.isChecked()) newPriority = 1;
            else if (high.isChecked()) newPriority = 2;

            mDatabaseHelper.updateTask(selectedID, newTitle, newDescription, newPriority);

            Intent intent = getIntent();
            intent.putExtra(DailyTaskViewActivity.TASK_KEY, 1);
            setResult(DailyTaskViewActivity.RESULT_OK, intent);
            finish();
        }
    }

    public void onDelete(View view){
        mDatabaseHelper.deleteTask(selectedID);

        Intent intent = getIntent();
        intent.putExtra(DailyTaskViewActivity.TASK_KEY, 2);
        setResult(DailyTaskViewActivity.RESULT_OK, intent);
        finish();
    }
}
