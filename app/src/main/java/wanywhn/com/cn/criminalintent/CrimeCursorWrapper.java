package wanywhn.com.cn.criminalintent;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import wanywhn.com.cn.criminalintent.CrimeDbSchema.CrimeTable;

/**
 * Created by tender on 17-7-10.
 */

public class CrimeCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Crime getCrime(){
        String uuidString=getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title=getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date=getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved=getInt(getColumnIndex(CrimeTable.Cols.SOLVED));

        Crime crime=new Crime(UUID.fromString(uuidString));
        crime.setmTitle(title);
        crime.setmData(new Date(date));
        crime.setmSolved(isSolved!=0);
        return crime;
    }
}
