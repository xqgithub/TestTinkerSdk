package com.universal.aifun;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.multidex.MultiDex;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.entry.DefaultApplicationLike;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.universal.aifun.tinker.util.TinkerManager;

/**
 * Date:2022/2/10
 * Time:13:49
 * author:dimple
 */
@DefaultLifeCycle(
        application = "com.universal.aifun.SampleApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false
)
public class BaseApplication extends DefaultApplicationLike {

    public static Application myApplication;

    public BaseApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = getApplication();
        initConfig();


        TKExtManage extManage = TKExtManage.getInstance();
        // 设置第三方
        extManage.setTinker_base_id(BuildConfig.tinker_base_id);
        extManage.setTinker_id(BuildConfig.tinker_id);
        extManage.setTinker_message(BuildConfig.tinker_message);

    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //必须使用multiDex
        MultiDex.install(base);

        TinkerManager.setTinkerApplicationLike(this);

        //在 installed 之前设置
        TinkerManager.setUpgradeRetryEnable(true);

        //installTinker after load multiDex
        //or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(this);
        Tinker tinker = Tinker.with(getApplication());
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        //1.初始化AutoSize
        ScreenTools.getInstance().initAutoSize(myApplication, myApplication);
        //2.日志类加载初始化
        new LogUtils.Builder()
                .setLogSwitch(true)//设置log总开关，默认开
                //.setGlobalTag("CMJ")// 设置log全局标签，默认为空
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setBorderSwitch(false)// 输出日志是否带边框开关，默认开
                .setLogFilter(LogUtils.V);// log过滤器，和logcat过滤器同理，默认Verbose
    }


}
