package com.github.microkibaco.mk_aspectj;

import android.app.Application;

import com.github.microkibaco.autotrace.SensorsDataAPI;


/**
 * @author 杨正友(小木箱)于 2020/10/8 13 20 创建
 * @Email: yzy569015640@gmail.com
 * @Tel: 18390833563
 * @function description:
 */
public class MkApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化埋点 SDK
        SensorsDataAPI.init(this);
    }


}
