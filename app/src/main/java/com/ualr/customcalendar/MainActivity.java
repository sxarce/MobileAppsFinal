package com.ualr.customcalendar;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private CalendarView calendarView;
    private String selectedDate;
    private SQLiteDatabase sqLiteDataBse;
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editTextTextPersonName);
        CalendarView calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                selectedDate = Integer.toString(year) + Integer.toString(month) + Integer.toString(dayOfMonth);
                Log.d(TAG, "Here: "+selectedDate);
                ReadDatabase(view);
            }
        });

        try {

            mySQLiteDBHandler dbHandler = new mySQLiteDBHandler(this, "CalendarDataBase", null, 1);
            sqLiteDataBse = dbHandler.getWritableDatabase();
            sqLiteDataBse.execSQL("CREATE TABLE EventCalendar (Date TEXT, Event TEXT)");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertDatabase (View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date", selectedDate);
        contentValues.put("Event", editText.getText().toString());
        sqLiteDataBse.insert("EventCalendar", null, contentValues);
    }

    public void ReadDatabase (View view) {
        String query = "Select Event from EventCalendar where Date =" + selectedDate;
        try {
            Cursor cursor = sqLiteDataBse.rawQuery(query, null);
            cursor.moveToFirst();
            editText.setText(cursor.getString( 0));
        }
        catch (Exception e) {
            e.printStackTrace();
            editText.setText("");
        }

    }

}