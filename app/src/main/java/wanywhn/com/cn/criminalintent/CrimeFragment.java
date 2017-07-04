package wanywhn.com.cn.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.UUID;

import static android.content.ContentValues.TAG;

/**
 * Created by tender on 17-7-1.
 */

public class CrimeFragment extends Fragment {
    private Crime mCrime;
    private EditText mTitleEdit;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private TextView mTitleField;

    private static final String ARG_CRIME_ID="crime_id";
    public static CrimeFragment newInstance(UUID crimeID){
        Bundle args=new Bundle();
        args.putSerializable(ARG_CRIME_ID,crimeID);
        CrimeFragment fragment=new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_crime,container,false);

        Log.i(TAG, "onCreateView: CREATED");
        mDateButton= (Button) v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getmData().toString());
        mDateButton.setEnabled(false);

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: CREATED");
//        UUID crimeID= (UUID) getActivity().getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
        Bundle arguments = getArguments();
        UUID crimeID = (UUID) arguments.getSerializable(ARG_CRIME_ID);
        mCrime=CrimeLab.getmCrimeLab(getActivity()).getCrime(crimeID);
    }
}
