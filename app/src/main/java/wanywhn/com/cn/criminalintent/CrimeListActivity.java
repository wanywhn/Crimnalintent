package wanywhn.com.cn.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by tender on 17-7-3.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
