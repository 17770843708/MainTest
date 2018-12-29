package com.liuchen.permissions;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 权限申请测试
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
    private static final String TAG = "MainActivity";
    private static final int PERMISSION_REQUEST = 123;
    private Button easyPermissionsBtn;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        easyPermissionsBtn = findViewById(R.id.easyPermissionsBtn);
        textView = findViewById(R.id.textView);
        easyPermissionsBtn.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将处理结果托管给EasyPermissions进行处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    /**
     * 测试EasyPermissions
     */
    private void testEasyPermissions() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this,perms)){
            Log.e(TAG, "已获取权限");
            textView.setText("已获取权限");
        }else {
            EasyPermissions.requestPermissions(this, "请求权限",
                    PERMISSION_REQUEST, perms);
            Log.e(TAG, "未获取权限");
            textView.setText("未获取权限");
        }
    }

    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.easyPermissionsBtn:
                testEasyPermissions();
                break;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.e(TAG, "onPermissionsGranted: ");
        textView.setText("权限请求成功");

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.e(TAG, "onPermissionsDenied: ");
        textView.setText("权限请求失败");
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
            textView.setText("需要进入系统设置授予权限");
        }
    }
}
