package com.huanxindemo20170323.register;

/**
 * Created by android on 2017/3/23.
 */
public interface RegisterContract {
    interface View {
        void setError(int errorCode);
        void setUserInfo(String username,String pwd);
    }

    interface Model {
        void onError(int errorCode);

        void onSuccess(String username,String pwd);
    }
}
