package com.universal.aifun.ui

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.universal.aifun.ConfigConstants
import com.universal.aifun.LogUtils
import com.universal.aifun.PublicPracticalMethodFromJAVA
import com.universal.aifun.R
import kotlinx.android.synthetic.main.activity_startup.*

/**
 * Date:2021/10/26
 * Time:15:58
 * author:joker
 * 启动页
 */
class StartUpActivity : BaseActivity(), StartUpView {
    private val mPresenter: StartUpPresenter by lazy { StartUpPresenter(this, this) }

    override fun onBeforeSetContentLayout() {
        //1.判断是pad还是phone，设置横屏或者竖屏
//        if (ScreenTools.getInstance().isPad(this)) {
//            //隐藏状态栏
//            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//            //设置横屏
//            ScreenTools.getInstance().setLandscape(this)
//        } else {
//            //状态栏状态设置
//            PublicPracticalMethodFromJAVA.getInstance().transparentStatusBar(this, false, true, -1)
//            //设置竖屏
//            ScreenTools.getInstance().setPortrait(this)
//        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_startup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PublicPracticalMethodFromJAVA.getInstance().restartApp(this)
    }

    override fun initUiView() {
    }

    override fun initData() {
        PublicPracticalMethodFromJAVA.getInstance().getPhoneScreenInfo(this)

        //开启计时器
        mPresenter.startTimerTask()

        //设置启动页图标
        mPresenter.setLogo { drawDrawableID: Int ->
            //获取图片的信息
            iv_logo.setImageDrawable(ContextCompat.getDrawable(this, drawDrawableID))
        }
    }

    override fun initListener() {
        mPresenter.setPermissionsListener(object : PermissionsActivity.PermissionsListener {

            //允许申请的权限
            override fun allPermissionsGranted(mark: Int) {
                if (mark == ConfigConstants.PERMISSIONS_GRANTED_STARTUPACTIVITY) {
                    LogUtils.i(ConfigConstants.TAG_ALL, "权限允许")
                    PublicPracticalMethodFromJAVA.getInstance().intentToJump(
                        this@StartUpActivity, TestTinkerActivity::class.java, -1, null, true,
                        R.anim.activity_right_in, R.anim.activity_right_out
                    )
                }
            }

            //拒绝申请的权限
            override fun permissionsDenied(mark: Int) {
                if (mark == ConfigConstants.PERMISSIONS_GRANTED_STARTUPACTIVITY) {
                    LogUtils.i(ConfigConstants.TAG_ALL, "权限被拒")
                }
            }
        })
    }

    override fun showLoading() {
        LogUtils.i(ConfigConstants.TAG_ALL, "开始加载")
    }

    override fun hideLoading() {
        LogUtils.i(ConfigConstants.TAG_ALL, "停止加载")
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.clearHandler()
    }


}