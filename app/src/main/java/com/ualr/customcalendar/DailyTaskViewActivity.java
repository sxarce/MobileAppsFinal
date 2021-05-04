package com.ualr.customcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailytaskview);
        mDatabaseHelper = new DatabaseHelper(this);
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
        if (requestCode == TASK_CODE_REQUEST && resultCode == RESULT_OK){
            Task newTask = data.getParcelableExtra(TASK_KEY);
        }
    }

}
