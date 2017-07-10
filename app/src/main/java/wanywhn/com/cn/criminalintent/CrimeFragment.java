package wanywhn.com.cn.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import static android.content.ContentValues.TAG;

/**
 * Created by tender on 17-7-1.
 */

public class CrimeFragment extends Fragment {
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME= 1;
    private Crime mCrime;
    private EditText mTitleEdit;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private TextView mTitleField;
    private Button mTimeButton;

    private static final String ARG_CRIME_ID="crime_id";
    private static final String DIALOG_DATE="DialogDate";
    public static final String DIALOG_TIME="DialogTime";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!= Activity.RESULT_OK){
            return;
        }
        switch (requestCode){
            case REQUEST_TIME:
                Calendar calendar=new GregorianCalendar();
                calendar.setTime(mCrime.getmData());
                Date date= (Date) data.getSerializableExtra(TimePickerFragment.TIMT);
                Calendar TimeCalendar=new GregorianCalendar();
                TimeCalendar.setTime(date);
                calendar.set(Calendar.HOUR,TimeCalendar.get(Calendar.HOUR));
                calendar.set(Calendar.MINUTE,TimeCalendar.get(Calendar.MINUTE));
                mCrime.setmData(calendar.getTime());
                break;
            case REQUEST_DATE:
                mCrime.setmData((Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE));
                mDateButton.setText(mCrime.getmData().toString());
                break;
        }
    }

    public static CrimeFragment newInstance(UUID crimeID){
        Bundle args=new Bundle();
        args.putSerializable(ARG_CRIME_ID,crimeID);
        CrimeFragment fragment=new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_viewer,menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_crime,container,false);

        Log.i(TAG, "onCreateView: CREATED");
        mTimeButton= (Button) v.findViewById(R.id.crime_time);
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getFragmentManager();
                TimePickerFragment dialog=TimePickerFragment.newInstatce(mCrime.getmData());
                dialog.setTargetFragment(CrimeFragment.this,REQUEST_TIME);
                dialog.show(fragmentManager,DIALOG_TIME);
            }
        });
        mDateButton= (Button) v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getmData().toString());
//        mDateButton.setEnabled(false);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment dialog=DatePickerFragment.newInstance(mCrime.getmData());
                dialog.setTargetFragment(CrimeFragment.this,REQUEST_DATE);
                dialog.show(fragmentManager,DIALOG_DATE);
            }
        });


        mSolvedCheckBox= (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.ismSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setmSolved(isChecked);
            }
        });
        mTitleEdit= (EditText) v.findViewById(R.id.crime_title);
        mTitleEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mCrime.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTitleField= (TextView) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getmTitle());
        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_delete:
                CrimeLab.getmCrimeLab(getActivity()).delCrime(mCrime);
//                Intent data=new Intent();
//                data.putExtra(CrimeListFragment.CHANG_ID,CrimeLab.getmCrimeLab(getActivity()).getCrimes().indexOf(mCrime));
//                getActivity().getParent().getFragmentManager().findFragmentById(R.id.crime_recycler_view).onActivityResult(CrimeListFragment.START_LIST_ITEM_CRIME,Activity.RESULT_OK,data);

                this.getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: CREATED");
//        UUID crimeID= (UUID) getActivity().getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
        Bundle arguments = getArguments();
        UUID crimeID = (UUID) arguments.getSerializable(ARG_CRIME_ID);
        mCrime=CrimeLab.getmCrimeLab(getActivity()).getCrime(crimeID);
        setHasOptionsMenu(true);
    }
}
