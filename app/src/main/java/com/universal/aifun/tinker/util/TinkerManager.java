package com.universal.aifun.tinker.util;

import com.tencent.tinker.entry.ApplicationLike;
import com.tencent.tinker.lib.listener.PatchListener;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.lib.util.UpgradePatchRetry;
import com.universal.aifun.ConfigConstants;
import com.universal.aifun.LogUtils;
import com.universal.aifun.tinker.reporter.CustomPatchListener;
import com.universal.aifun.tinker.service.CustomResultService;

/**
 * Date:2021/11/23
 * Time:13:16
 * author:dimple
 */
public class TinkerManager {


    private static ApplicationLike applicationLike;
    /**
     * 保证只初始化一次
     */
    private static boolean isInstalled = false;

    public static void setTinkerApplicationLike(ApplicationLike appLike) {
        applicationLike = appLike;
    }

    public static ApplicationLike getTinkerApplicationLike() {
        return applicationLike;
    }


    public static void setUpgradeRetryEnable(boolean enable) {
        UpgradePatchRetry.getInstance(applicationLike.getApplication()).setRetryEnable(enable);
    }

    public static void installTinker(ApplicationLike appLike) {
        if (isInstalled) {
            LogUtils.i(ConfigConstants.TAG_ALL, "install tinker, but has installed, ignore");
            return;
        }
        //监听patch文件加载过程中的事件
        LoadReporter loadReporter = new DefaultLoadReporter(appLike.getApplication());
        //监听patch文件合成过程中的事件
        PatchReporter patchReporter = new DefaultPatchReporter(appLike.getApplication());
        //监听patch文件接收到之后可以做一些校验
        PatchListener patchListener = new CustomPatchListener(appLike.getApplication());
        //升级策略
        AbstractPatch upgradePatchProcessor = new UpgradePatch();

        TinkerInstaller.install(appLike,
                loadReporter, patchReporter, patchListener,
                CustomResultService.class, upgradePatchProcessor);
        isInstalled = true;
    }

}
