
package com.huanxindemo20170323.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huanxindemo20170323.MainActivity;
import com.huanxindemo20170323.R;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.adapter.EMAChatManager;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    private EditText et_username, et_pwd;
    private Button btn_register;
    private String username, pwd1;
    //输入框得到的用户名
    private String name;
    //输入框得到的密码
    private String pwd;
    private LoginPresenter mPresenter;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = getIntent().getStringExtra("username");
        pwd1 = getIntent().getStringExtra("pwd");
        initView();
    }

    private void initView() {
        mPresenter = new LoginPresenter(this);
        et_username = (EditText) findViewById(R.id.et_username);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        if (username != null && !TextUtils.isEmpty(username)) {
            et_username.setText(username);
            et_pwd.setText(pwd1);
        }
    }

    public void loginClick(View view) {
        name = et_username.getText().toString().trim();
        pwd = et_pwd.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(LoginActivity.this, "用户名或者密码为空", Toast.LENGTH_SHORT).show();
        } else {
            //创建一个进度条对话框实例，
             pd = new ProgressDialog(LoginActivity.this);
            //设置点击进度条边框以外的区域无响应
            pd.setCanceledOnTouchOutside(false);
            //添加对话框提示
            pd.setMessage("正在登录...");
            //让对话框显示
            pd.show();
            mPresenter.login(name,pwd,null);

        }
    }

    @Override
    public void setError(final int code) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (code == 200) {
                    Toast.makeText(LoginActivity.this, "用户已经登陆", Toast.LENGTH_SHORT).show();
                } else {
                Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                }
                Log.e("duanlian", "run: code==="+ code);

            }
        });

    }

    @Override
    public void setUserInfo(final String username) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                intent.putExtra("username", username);
                startActivity(intent);
            }
        });


    }
}
