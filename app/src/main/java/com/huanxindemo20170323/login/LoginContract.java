package com.huanxindemo20170323.login;

/**
 * Created by android on 2017/3/23.
 */
public class LoginContract {
    interface View {
        void setError(int code);
        void setUserInfo(String username);
    }

    interface Model {
        void onError(int code);

        void onSuccess(String username);
    }
}
