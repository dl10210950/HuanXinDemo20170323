package com.huanxindemo20170323.login;

/**
 * Created by android on 2017/3/23.
 */
public interface LoginInt {
    void login(String username, String pwd, LoginContract.Model listener);
}
