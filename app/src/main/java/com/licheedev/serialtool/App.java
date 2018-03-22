package com.licheedev.serialtool;

import android.app.Application;
import android.os.Handler;
import com.licheedev.serialtool.util.PrefHelper;

/**
 * Created by Administrator on 2017/3/28 0028.
 */

public class App extends Application {

    private Handler mUiHandler;
    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        mUiHandler = new Handler();
        initUtils();
    }

    private void initUtils() {
        PrefHelper.initDefault(this);
    }

    public static App instance() {
        return sInstance;
    }

    public static Handler getUiHandler() {
        return instance().mUiHandler;
    }
}
