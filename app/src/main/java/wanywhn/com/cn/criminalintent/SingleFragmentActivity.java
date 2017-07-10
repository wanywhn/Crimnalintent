package wanywhn.com.cn.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by tender on 17-7-3.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentById(R.id.frame_container);
        if (fragment == null) {
            fragment=createFragment();
            supportFragmentManager.beginTransaction()
                    .add(R.id.frame_container,fragment)
                    .commit();
        }
    }
    protected abstract Fragment createFragment();
}
