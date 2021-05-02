package com.ualr.customcalendar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;


public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = TimePickerDialogFragment.class.getSimpleName();

    NoticeDialogListener listener;

    public interface NoticeDialogListener{
        public void onDialogTimeClick(String date);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DatePickerDialogFragment.NoticeDialogListener) {
            listener = (TimePickerDialogFragment.NoticeDialogListener) context;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));

        return timePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        Log.d(TAG, String.format("Time selected- %d h %d min", hourOfDay, minute));
        String time = "";
        if(hourOfDay > 12){
            time = (hourOfDay - 12) + ":" + minute + "PM";
        }
        else{
            time = hourOfDay + ":" + minute + "AM";
        }
        listener.onDialogTimeClick(time);

    }
}

