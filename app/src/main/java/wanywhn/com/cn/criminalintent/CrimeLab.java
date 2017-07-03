package wanywhn.com.cn.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by tender on 17-7-2.
 */

public class CrimeLab {
    private static CrimeLab mCrimeLab;
     private List<Crime> mCrimes;

    private CrimeLab(Context context) {
        mCrimes=new ArrayList<>();
        for (int i=0;i!=100;i++){
            Crime crime=new Crime();
            crime.setmTitle("Crime #"+1);
            crime.setmSolved(i%2==0);
            mCrimes.add(crime);
        }
    }

    public static CrimeLab getmCrimeLab(Context context) {
        if (mCrimeLab == null) {
            mCrimeLab=new CrimeLab(context);
        }
        return mCrimeLab;
    }
    public List<Crime> getCrimes(){
        return mCrimes;
    }
    public Crime getCrime(UUID id){
        for(Crime crime:mCrimes){
            if (crime.getmId().equals(id)){
                return crime;
            }
        }
        return null;
    }

}
