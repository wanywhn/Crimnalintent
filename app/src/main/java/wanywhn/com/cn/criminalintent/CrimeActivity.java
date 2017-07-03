package wanywhn.com.cn.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

public class CrimeActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentById(R.id.frame_container);
        if (fragment == null) {
            fragment=new CrimeFragment();
            supportFragmentManager.beginTransaction()
                    .add(R.id.frame_container,fragment)
                    .commit();
        }
    }
}
