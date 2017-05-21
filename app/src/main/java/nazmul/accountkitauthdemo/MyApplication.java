package nazmul.accountkitauthdemo;

import android.app.Application;

import com.facebook.accountkit.AccountKit;

/**
 * Created by Nazmul on 5/21/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AccountKit.initialize(getApplicationContext());
    }
}
