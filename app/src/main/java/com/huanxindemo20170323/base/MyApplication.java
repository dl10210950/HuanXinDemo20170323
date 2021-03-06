package com.huanxindemo20170323.base;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

/**
 * Created by user on 2016/7/4.
 */
public class MyApplication extends Application {
    //声明应用的上下文
    private Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        //项目上下文对象的创建
        applicationContext = this;
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
        //初始化
        EMClient.getInstance().init(applicationContext, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }
}
