package wanywhn.com.cn.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import wanywhn.com.cn.criminalintent.CrimeDbSchema.CrimeTable;

/**
 * Created by tender on 17-7-2.
 */

public class CrimeLab {
    private static CrimeLab mCrimeLab;
    //     private List<Crime> mCrimes;
    private Context mContext;
    private static SQLiteDatabase mDatabase;

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
//        mCrimes=new ArrayList<>();
    }

    public void addCrime(Crime c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
//        mCrimes.add(c);
    }

    public static void updateCrime(Crime crime) {
        String uuidString = crime.getmId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + "= ?"
                , new String[]{uuidString});

    }
    private CrimeCursorWrapper queryCrimes(String whereClause,String[] whereArgs){
        Cursor  cursor =mDatabase.query(
                CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CrimeCursorWrapper(cursor);
    }

    public static CrimeLab getmCrimeLab(Context context) {
        if (mCrimeLab == null) {
            mCrimeLab = new CrimeLab(context);
        }
        return mCrimeLab;
    }

    public List<Crime> getCrimes() {
        List<Crime> crimes=new ArrayList<>();

        CrimeCursorWrapper cursorWrapper=queryCrimes(null,null);
        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                crimes.add(cursorWrapper.getCrime());
                cursorWrapper.moveToNext();
            }
        }finally {
            cursorWrapper.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id) {
        CrimeCursorWrapper cursor=queryCrimes(
                CrimeTable.Cols.UUID+ "= ?",
                new String[] {id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }
    }

    public void delCrime(Crime mCrime) {
        String uuidString =mCrime.getmId().toString();
        mDatabase.delete(CrimeTable.NAME,CrimeTable.Cols.UUID+" =?"
                ,new String[]{uuidString});
    }

    public static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getmId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getmTitle());
        values.put(CrimeTable.Cols.DATE, crime.getmData().toString());
        values.put(CrimeTable.Cols.SOLVED, crime.ismSolved() ? 1 : 0);
        return values;
    }
}
