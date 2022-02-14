package com.universal.aifun;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/**
 * Author  guoyw
 * Date    2021/7/29
 * Desc    第三方定制管理
 */
public class TKExtManage {

    private static final String APP_LOGO = "APP_LOGO";
    private static final String APP_NAME = "APP_NAME";
    private static final String START_LOGO = "START_LOGO";
    private static final String APP_SCHEME = "APP_SCHEME";

    private static volatile TKExtManage mInstance;

    //tinker_base_id
    private String tinker_base_id = "";
    //tinkerId
    private String tinker_id = "";
    //tinkerMessage
    private String tinker_message = "";


    private TKExtManage() {
        if (mInstance != null) {
            throw new RuntimeException("instance is exist!");
        }
    }

    public static TKExtManage getInstance() {
        if (mInstance == null) {
            synchronized (TKExtManage.class) {
                if (mInstance == null) {
                    mInstance = new TKExtManage();
                }
            }
        }
        return mInstance;
    }

    public String getTinker_base_id() {
        return tinker_base_id;
    }

    public void setTinker_base_id(String tinker_base_id) {
        this.tinker_base_id = tinker_base_id;
    }

    public String getTinker_id() {
        return tinker_id;
    }

    public void setTinker_id(String tinker_id) {
        this.tinker_id = tinker_id;
    }

    public String getTinker_message() {
        return tinker_message;
    }

    public void setTinker_message(String tinker_message) {
        this.tinker_message = tinker_message;
    }

    // 获取图标icon
    public int getAppLogoRes(Context context) {
        int appLogo = getAppRes(context, APP_LOGO);
        if (appLogo == 0) {
            appLogo = R.mipmap.ic_launcher;
        }
        return appLogo;
    }

    // 获取App Name 资源
    public int getAppNameRes(Context context) {
        int appName = getAppRes(context, APP_NAME);
        if (appName == 0) {
            appName = R.string.app_name;
        }
        return appName;
    }

    /**
     * 获取启动页图标
     *
     * @param context
     * @return
     */
    public int getStartLogo(Context context) {
        int startlogo = getAppRes(context, START_LOGO);
        if (startlogo == 0) {
            startlogo = R.mipmap.start_logo;
        }
        return startlogo;
    }


    /**
     * 获取 Scheme 数据
     *
     * @param context
     * @return
     */
    public String getAppScheme(Context context) {
        String scheme = "";
        Bundle bundle_metadata = getAppMetaData(context);
        if (!StringUtils.isBlank(bundle_metadata)) {
            scheme = bundle_metadata.getString(APP_SCHEME);
        }
        return scheme;
    }


    // 获取app 到资源
    public int getAppRes(Context context, String key) {

        Bundle metaData = null;
        int appRes = 0;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai)
                metaData = ai.metaData;
            if (null != metaData) {
                appRes = metaData.getInt(key);
//                if (appRes == 0) {
//                    appRes = R.string.app_name;
//                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appRes;
    }

    /**
     * 获取AppMetaData数据
     *
     * @param context
     * @return
     */
    public Bundle getAppMetaData(Context context) {
        Bundle metaData = null;
        int appRes = 0;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai)
                metaData = ai.metaData;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return metaData;
    }

}
