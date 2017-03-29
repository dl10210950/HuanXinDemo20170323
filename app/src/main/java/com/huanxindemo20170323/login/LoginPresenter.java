package com.huanxindemo20170323.login;

import com.huanxindemo20170323.register.RegisterContract;
import com.huanxindemo20170323.register.RegisterInt;
import com.huanxindemo20170323.register.RegisterModel;

/**
 * Created by android on 2017/3/23.
 */
public class LoginPresenter implements LoginContract.Model, LoginInt {
    private LoginContract.View view;
    private LoginModel model;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        model = new LoginModel();
    }



    @Override
    public void login(String username, String pwd, LoginContract.Model listener) {
        model.login(username,pwd,this);
    }

    @Override
    public void onError(int code) {
        view.setError(code);
    }

    @Override
    public void onSuccess(String username) {
        view.setUserInfo(username);
    }
}
