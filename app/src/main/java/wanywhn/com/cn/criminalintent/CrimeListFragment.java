package wanywhn.com.cn.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private final int START_LIST_ITEM_CRIME = 0;
    private View mChanged_View;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI( 0);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(mCrimeRecyclerView.getChildAdapterPosition(mChanged_View));
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(resultCode== Activity.RESULT_OK){
//            switch (requestCode){
//                case START_LIST_ITEM_CRIME:
//                    updateUI(data.getIntExtra(CHANG_ID,0));
//            }
//        }
//    }

    private void updateUI(int intExtra) {
        CrimeLab crimeLab = CrimeLab.getmCrimeLab(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
//            mAdapter.notifyDataSetChanged();
            mAdapter.notifyItemChanged(intExtra);
        }

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
            mChanged_View=v;
//            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getmId());
            Intent intent=CrimePagerActivity.newIntent(getActivity(),mCrime.getmId());
            startActivityForResult(intent, START_LIST_ITEM_CRIME);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mCrime.setmSolved(isChecked);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

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
