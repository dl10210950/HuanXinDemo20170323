package com.huanxindemo20170323;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huanxindemo20170323.chat.ChatActivity;
import com.huanxindemo20170323.login.LoginActivity;
import com.huanxindemo20170323.register.RegisterActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

public class MainActivity extends AppCompatActivity {
    private Button btn_login, btn_login_out;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        //判断是否登陆
        boolean loggedInBefore = EMClient.getInstance().isLoggedInBefore();
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login_out = (Button) findViewById(R.id.btn_login_out);
        editText = (EditText) findViewById(R.id.et_id);
        if (loggedInBefore) {
            btn_login.setText("已经登陆了");
            btn_login_out.setVisibility(View.VISIBLE);
        } else {
            btn_login.setText("登陆");
            btn_login_out.setVisibility(View.GONE);
        }

    }

    public void mainClick(View view) {
        Intent intent = new Intent();
        //判断是否登陆
        boolean loggedInBefore = EMClient.getInstance().isLoggedInBefore();
        switch (view.getId()) {
            case R.id.btn_register:
                if (loggedInBefore) {
                    Toast.makeText(MainActivity.this, "先退出登陆才能去注册", Toast.LENGTH_SHORT).show();
                } else {
                    intent.setClass(MainActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.btn_login:
                if (loggedInBefore) {
                    Toast.makeText(MainActivity.this, "先退出登陆才能去登陆", Toast.LENGTH_SHORT).show();
                } else {
                    intent.setClass(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.btn_login_out:
                loginOut();
                break;
            case R.id.btn_to_chat:

                if (loggedInBefore) {
                    String friend = editText.getText().toString().trim();
                    if (TextUtils.isEmpty(friend)) {
                        Toast.makeText(MainActivity.this, "输入为空", Toast.LENGTH_SHORT).show();
                    } else {
                        intent.putExtra("friend", friend);
                        intent.setClass(MainActivity.this, ChatActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "先去登陆", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void loginOut() {
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                //判断是否登陆
                final boolean loggedInBefore = EMClient.getInstance().isLoggedInBefore();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (loggedInBefore) {
                            btn_login.setText("已经登陆了");
                            btn_login_out.setVisibility(View.VISIBLE);
                        } else {
                            btn_login.setText("登陆");
                            btn_login_out.setVisibility(View.GONE);
                        }
                    }
                });

            }

            @Override
            public void onError(int i, String s) {
                //判断是否登陆
                boolean loggedInBefore = EMClient.getInstance().isLoggedInBefore();
                Log.e("duanlian", "onError: loggedInBefore===" + loggedInBefore);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
