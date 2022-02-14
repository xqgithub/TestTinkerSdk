package com.universal.aifun.ui

import android.app.AlertDialog
import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.tencent.tinker.lib.tinker.Tinker
import com.tencent.tinker.lib.tinker.TinkerInstaller
import com.tencent.tinker.loader.shareutil.ShareConstants
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals
import com.universal.aifun.*

class TestTinkerPresenter constructor(private var mContext: Context, private var mView: TestTinkerView) : BasePresenter<TestTinkerView>(mContext, mView) {

    /**
     * 加载补丁
     */
    fun loadPath() {
        val path = SDCardUtils.getExternalStorageDirectory().absolutePath + "/patch_signed_7zip.apk"
        if (FileUtils.isFileExists(path)) {
            LogUtils.i(ConfigConstants.TAG_ALL, "补丁存在")
            TinkerInstaller.onReceiveUpgradePatch(mContext, path)
        } else {
            LogUtils.i(ConfigConstants.TAG_ALL, "补丁不存在")
        }


    }

    /**
     * 清除补丁
     */
    fun clearPath() {
//        val path = SDCardUtils.getExternalStorageDirectory().absolutePath + "/patch_signed_7zip.apk"
//        if (FileUtils.isFileExists(path)) {
//            FileUtils.deleteDir(path)
//        } else {
//            LogUtils.i(ConfigConstants.TAG_ALL, "补丁不存在")
//        }
        Tinker.with(mContext).cleanPatch();

    }

    /**
     * 显示信息
     */
    fun showInfo(): Boolean {
        val sb = StringBuilder()
        val tinker = Tinker.with(mContext)
        if (tinker.isTinkerLoaded) {
            sb.append(String.format("[patch is loaded] \n"))
            sb.append(String.format("[buildConfig TINKER_ID] %s \n", TKExtManage.getInstance().tinker_id))
            sb.append(String.format("[buildConfig BASE_TINKER_ID] %s \n", TKExtManage.getInstance().tinker_base_id))

            sb.append(String.format("[buildConfig MESSSAGE] %s \n", TKExtManage.getInstance().tinker_message))
            sb.append(String.format("[TINKER_ID] %s \n", tinker.tinkerLoadResultIfPresent.getPackageConfigByName(ShareConstants.TINKER_ID)))
            sb.append(String.format("[packageConfig patchMessage] %s \n", tinker.tinkerLoadResultIfPresent.getPackageConfigByName("patchMessage")))
            sb.append(String.format("[TINKER_ID Rom Space] %d k \n", tinker.tinkerRomSpace))
        } else {
            sb.append(String.format("[patch is not loaded] \n"))
            sb.append(String.format("[buildConfig TINKER_ID] %s \n", TKExtManage.getInstance().tinker_id))
            sb.append(String.format("[buildConfig BASE_TINKER_ID] %s \n", TKExtManage.getInstance().tinker_base_id))
             sb.append(String.format("[buildConfig MESSSAGE] %s \n", TKExtManage.getInstance().tinker_message))
            sb.append(String.format("[TINKER_ID] %s \n", ShareTinkerInternals.getManifestTinkerID(mContext)))
        }
        sb.append(String.format("[BaseBuildInfo Message] %s \n", ""))

        val v = TextView(mContext)
        v.text = sb
        v.gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
        v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10f)
        v.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        v.setTextColor(-0x1000000)
        v.typeface = Typeface.MONOSPACE
        val padding = 16
        v.setPadding(padding, padding, padding, padding)

        val builder: AlertDialog.Builder = AlertDialog.Builder(mContext)
        builder.setCancelable(true)
        builder.setView(v)
        val alert: AlertDialog = builder.create()
        alert.show()
        return true
    }

}
