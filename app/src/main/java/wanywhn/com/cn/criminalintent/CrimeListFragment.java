package wanywhn.com.cn.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by tender on 17-7-3.
 */

public class CrimeListFragment extends Fragment {
    public static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    public static final String CHANG_ID = "changed id";

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    public static final int START_LIST_ITEM_CRIME = 0;
    private final int DELETE_CRIME_ITEM = 1;
    private View mChanged_View;
    private boolean mSubtitleVisible;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.getmCrimeLab(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getmId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI(-1);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (mChanged_View != null)
//            updateUI(mCrimeRecyclerView.getChildAdapterPosition(mChanged_View));
//        else {
            updateUI(-1);
//        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(resultCode== Activity.RESULT_OK){
//            switch (requestCode){
//                case START_LIST_ITEM_CRIME:
//                    updateUI(data.getIntExtra(CHANG_ID,0));
//                    break;
//            }
//        }
//    }

    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.getmCrimeLab(getActivity());
        int size = crimeLab.getCrimes().size();
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_format,size,size);
        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI(int intExtra) {
        CrimeLab crimeLab = CrimeLab.getmCrimeLab(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        mCrimeRecyclerView.setVisibility(View.VISIBLE);
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setmCrimes(crimes);
                mAdapter.notifyDataSetChanged();
        }
        updateSubtitle();

    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        private CheckBox mSolvedCheckBox;
        private TextView mCrimeTitleTextView;
        private TextView mCrimeDateTextView;
        private Crime mCrime;

        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
//            mTitleTextView= (TextView) itemView;
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
            mCrimeTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mCrimeDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
        }

        public void bindCrime(Crime crime) {
            mCrime = crime;
            mSolvedCheckBox.setChecked(mCrime.ismSolved());
            mSolvedCheckBox.setOnCheckedChangeListener(this);
            mCrimeTitleTextView.setText(mCrime.getmTitle());
            mCrimeDateTextView.setText(mCrime.getmData().toString());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), mCrime.getmTitle() + "Clicked", Toast.LENGTH_SHORT).show();
//            mCrimeRecyclerView.getAdapter().notifyItemMoved(mCrimeRecyclerView.getChildAdapterPosition(v), (int) (Math.random() % (mAdapter.getItemCount())));
            mChanged_View = v;
//            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getmId());
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getmId());
            startActivityForResult(intent, START_LIST_ITEM_CRIME);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mCrime.setmSolved(isChecked);
            CrimeLab.getmCrimeLab(getActivity()).updateCrime(mCrime);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public void setmCrimes(List<Crime> crimes){
            mCrimes=crimes;
        }
        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_litem_crime, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {

            Crime crime = mCrimes.get(position);
//            holder.mTitleTextView.setText(crime.getmTitle());
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
