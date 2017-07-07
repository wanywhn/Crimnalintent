package wanywhn.com.cn.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by tender on 17-7-7.
 */

public class TimePickerFragment extends DialogFragment{
    public static final String TIMT="DialogFragmnt_Time";
    private TimePicker mTimePicker;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date= (Date) getArguments().getSerializable(TIMT);
        Calendar calendar=new GregorianCalendar();
        calendar.setTime(date);
        int HH=calendar.get(Calendar.HOUR);
        int MM=calendar.get(Calendar.MINUTE);
        int SS=calendar.get(Calendar.SECOND);

        View v= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time,null);
        mTimePicker= (TimePicker) v.findViewById(R.id.dialog_time_time_picker);
        mTimePicker.setHour(HH);
        mTimePicker.setMinute(MM);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int HH=mTimePicker.getHour();
                        int MM=mTimePicker.getMinute();
                        Calendar calendar=new GregorianCalendar();
                        calendar.set(Calendar.HOUR,HH);
                        calendar.set(Calendar.MINUTE,MM);
                        sendResult(calendar.getTime());
                    }
                }).create();

    }

    private void sendResult(Date time) {
        if (getTargetFragment()==null){
            return;
        }
        Intent  intent=new Intent();
        intent.putExtra(TIMT,time);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
    }

    public static TimePickerFragment newInstatce(Date date) {

        Bundle bundle=new Bundle();
        bundle.putSerializable(TIMT,date);
        TimePickerFragment timePickerFragment=new TimePickerFragment();
        timePickerFragment.setArguments(bundle);
        return timePickerFragment;
    }
}
