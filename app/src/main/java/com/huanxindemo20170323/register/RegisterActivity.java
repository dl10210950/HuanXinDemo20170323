package com.huanxindemo20170323.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huanxindemo20170323.R;
import com.huanxindemo20170323.login.LoginActivity;
import com.hyphenate.EMError;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {
    private EditText et_username, et_pwd, et_pwd_confirm;
    private Button btn_register;
    private String name, pwd, confirm_pwd;
    private RegisterPresenter mPresenter;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_pwd_confirm = (EditText) findViewById(R.id.et_pwd_confirm);
        btn_register = (Button) findViewById(R.id.btn_register);

        mPresenter = new RegisterPresenter(this);
    }

    public void registerClick(View view) {
        name = et_username.getText().toString().trim();
        pwd = et_pwd.getText().toString().trim();
        confirm_pwd = et_pwd_confirm.getText().toString().trim();
        //判断用户名不为空
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(RegisterActivity.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //判断两次密码是否一致
        if (!pwd.equals(confirm_pwd)) {
            Toast.makeText(RegisterActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        // 创建一个进度条对话框
        pd = new ProgressDialog(RegisterActivity.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("正在注册中。。。");
        pd.show();
        //去注册
        mPresenter.register(name, pwd, null);

    }

    @Override
    public void setError(final int errorCode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (errorCode == EMError.USER_ALREADY_EXIST) {
                    Toast.makeText(RegisterActivity.this, "用户名已经存在", Toast.LENGTH_SHORT).show();
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                } else if (errorCode == EMError.NETWORK_ERROR) {
                    Toast.makeText(RegisterActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                } else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
                    Toast.makeText(RegisterActivity.this, "用户名非法", Toast.LENGTH_SHORT).show();
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                }
            }
        });

    }

    @Override
    public void setUserInfo(final String username, final String pwd) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("pwd", pwd);
                startActivity(intent);
            }
        });

    }
}