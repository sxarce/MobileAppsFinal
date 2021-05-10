package com.ualr.customcalendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    private static final String TAG = DatePickerDialogFragment.class.getSimpleName();

    NoticeDialogListener listener;

    public interface NoticeDialogListener{
        void onDialogDateClick(int nmonth, int nday, int nyear, String ndate);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof  NoticeDialogListener) {
            listener = (NoticeDialogListener) context;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(), this, year, month, day);

        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Log.d(TAG, String.format("Date selected- year %d, month %d, day %d", year, month, day));
        String date = String.format(" %d/%d/%d", month+1, day, year);
        listener.onDialogDateClick(month+1, day, year, date);
    }


}
