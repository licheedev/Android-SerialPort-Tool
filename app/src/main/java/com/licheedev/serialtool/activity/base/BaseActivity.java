package com.licheedev.serialtool.activity.base;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.licheedev.serialtool.R;
import com.licheedev.serialtool.fragment.LogFragment;
import com.licheedev.serialtool.comn.message.IMessage;
import com.licheedev.serialtool.comn.message.LogManager;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseActivity extends AppCompatActivity {

    protected ActionBar mActionBar;
    private LogFragment mLogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        if (hasActionBar()) {
            mActionBar = getSupportActionBar();
        }
        initFragment();
    }

    /**
     * 获取布局id
     *
     * @return
     */
    protected abstract int getLayoutId();

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLogList();
    }

    /**
     * 刷新日志列表
     */
    protected void refreshLogList() {
        mLogFragment.updateAutoEndButton();
        mLogFragment.updateList();
    }

    /**
     * 初始化日志Fragment
     */
    protected void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mLogFragment = (LogFragment) fragmentManager.findFragmentById(R.id.log_fragment);
    }

    /**
     * 添加日志
     *
     * @param message
     */
    protected void addLog(IMessage message) {
        LogManager.instance().add(message);
        refreshLogList();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    protected boolean hasActionBar() {
        return true;
    }

    protected void setActionBar(boolean showUp, String title) {
        mActionBar.setHomeButtonEnabled(showUp);
        mActionBar.setDisplayHomeAsUpEnabled(showUp);
        mActionBar.setTitle(title);
    }

    protected void setActionBar(boolean showUp, int stringResId) {
        mActionBar.setHomeButtonEnabled(showUp);
        mActionBar.setDisplayHomeAsUpEnabled(showUp);
        mActionBar.setTitle(stringResId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(IMessage message) {
        // 收到时间，刷新界面
        mLogFragment.add(message);
    }
}
