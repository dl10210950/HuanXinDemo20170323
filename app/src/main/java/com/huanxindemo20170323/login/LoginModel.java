package com.huanxindemo20170323.login;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Created by android on 2017/3/23.
 */
public class LoginModel implements LoginInt{

    @Override
    public void login(final String username, String pwd, final LoginContract.Model listener) {
        EMClient.getInstance().login(username, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                listener.onSuccess(username);
            }

            @Override
            public void onError(int code, String error) {
                listener.onError(code);
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }
}
