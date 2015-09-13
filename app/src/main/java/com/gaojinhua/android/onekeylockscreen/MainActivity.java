package com.gaojinhua.android.onekeylockscreen;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.security.Permission;
import java.security.Permissions;

public class MainActivity extends AppCompatActivity {
    private DevicePolicyManager dpm;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);

        if (isAdmin()) {
            lockScreen();
        } else {
            openAdmin();
            sp.edit().putBoolean("admin", true).commit();
        }
        finish();
    }

    public boolean isAdmin() {
        boolean admin;
        admin = sp.getBoolean("admin", false);
        return admin;
    }

    public void lockScreen() {
        dpm.lockNow();//锁屏
        //dpm.resetPassword("", 0);//""处，设置开屏密码
        //dpm.wipeData(0);//0恢复出厂设置
    }


    public void openAdmin() {
        //创建一个Intent
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        //我要激活谁
        ComponentName mDeviceAdminSample = new ComponentName(this, MyAdmin.class);

        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample);
        //劝说用户开启管理员权限
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "开启一键锁屏，第一次安装需要开启权限，开启后点击桌面一键锁屏图标锁屏");
        startActivity(intent);
    }
}
