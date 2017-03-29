package com.huanxindemo20170323.register;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * 注册
 */
public class RegisterModel implements RegisterInt {
    @Override
    public void register(final String username, final String pwd, final RegisterContract.Model listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(username, pwd);
                    listener.onSuccess(username,pwd);
                } catch (HyphenateException e) {
                    int errorCode = e.getErrorCode();
                    listener.onError(errorCode);
                }
            }
        }).start();
    }
}
