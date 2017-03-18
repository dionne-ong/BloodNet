package edu.mobapde.bloodnet.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import edu.mobapde.bloodnet.EditProfileActivity;
import edu.mobapde.bloodnet.EditProfileFABActivity;

/**
 * Created by psion on 3/18/2017.
 */

public class DatePickerFragment extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener)getActivity(), year, month, day);
        dialog.getDatePicker().setMaxDate((new Date()).getTime());

        // Create a new instance of  and return it
        return dialog;
    }

}