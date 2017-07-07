package wanywhn.com.cn.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by tender on 17-7-1.
 */

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mData;
    private boolean mSolved;

    public UUID getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getmData() {
        return mData;
    }

    public void setmData(Date mData) {
        this.mData = mData;
    }

    public boolean ismSolved() {
        return mSolved;
    }

    public void setmSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public Crime(){
        mId= UUID.randomUUID();
        mData=new Date();

    }

}
