package com.huanxindemo20170323.register;

/**
 * Created by android on 2017/3/23.
 */
public class RegisterPresenter implements RegisterContract.Model, RegisterInt {
    private RegisterContract.View view;
    private RegisterModel model;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        model = new RegisterModel();
    }

    @Override
    public void onError(int errorCode) {
        view.setError(errorCode);
    }

    @Override
    public void onSuccess(String username,String pwd) {
        view.setUserInfo(username,pwd);
    }

    @Override
    public void register(String username, String pwd, RegisterContract.Model listener) {
        model.register(username,pwd,this);
    }
}
