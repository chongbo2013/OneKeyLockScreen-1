package com.gaojinhua.android.onekeylockscreen;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 100;
    private DevicePolicyManager dpm;
    private ComponentName mComponentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mComponentName = new ComponentName(this, MyAdmin.class);
        dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);

        if (dpm.isAdminActive(mComponentName)) {
            lockScreen();
            finish();
        } else {
            openAdmin();
        }
    }


    public void lockScreen() {
        dpm.lockNow();//锁屏
        //dpm.resetPassword("", 0);//""处，设置开屏密码
        //dpm.wipeData(0);//0恢复出厂设置
    }


    public void openAdmin() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "开启一键锁屏，第一次安装需要开启权限");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            lockScreen();
            finish();
        } else {
            openAdmin();
        }
    }
}
