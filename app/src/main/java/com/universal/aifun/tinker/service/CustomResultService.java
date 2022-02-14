package com.universal.aifun.tinker.service;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.tencent.tinker.lib.service.DefaultTinkerResultService;
import com.tencent.tinker.lib.service.PatchResult;
import com.tencent.tinker.lib.util.TinkerServiceInternals;
import com.universal.aifun.ConfigConstants;
import com.universal.aifun.LogUtils;
import com.universal.aifun.tinker.util.Utils;

import java.io.File;

/**
 * 决定在patch安装完以后的后续操作，默认实现是杀进程
 */
public class CustomResultService extends DefaultTinkerResultService {

    //返回patch文件的结果
    @Override
    public void onPatchResult(final PatchResult result) {
        if (result == null) {
            LogUtils.i(ConfigConstants.TAG_ALL, "CustomResultService received null result!!!!");
            return;
        }
        LogUtils.i(ConfigConstants.TAG_ALL, "CustomResultService receive result: %s", result.toString());

        //first, we want to kill the recover process
        TinkerServiceInternals.killTinkerPatchServiceProcess(getApplicationContext());

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (result.isSuccess) {
                    Toast.makeText(getApplicationContext(), "patch success, please restart process", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "patch fail, please check reason", Toast.LENGTH_LONG).show();
                }
            }
        });
        // is success and newPatch, it is nice to delete the raw file, and restart at once
        // for old patch, you can't delete the patch file
        if (result.isSuccess) {
            deleteRawPatchFile(new File(result.rawPatchFilePath));

            //默认是直接重启体验可能不好，这里只是在后台重启
            if (checkIfNeedKill(result)) {
                if (Utils.isBackground()) {
                    LogUtils.i(ConfigConstants.TAG_ALL, "it is in background, just restart process");
                    restartProcess();
                } else {
                    LogUtils.i(ConfigConstants.TAG_ALL, "tinker wait screen to restart process");
                    new Utils.ScreenState(getApplicationContext(), new Utils.ScreenState.IOnScreenOff() {
                        @Override
                        public void onScreenOff() {
                            restartProcess();
                        }
                    });
                }
            } else {
                LogUtils.i(ConfigConstants.TAG_ALL, "I have already install the newly patch version!");
            }
        }
    }

    /**
     * you can restart your process through service or broadcast
     */
    private void restartProcess() {
        LogUtils.i(ConfigConstants.TAG_ALL, "app is background now, i can kill quietly");
        //you can send service or broadcast intent to restart your process
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
