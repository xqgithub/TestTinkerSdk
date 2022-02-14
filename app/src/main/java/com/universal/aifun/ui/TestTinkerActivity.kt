package com.universal.aifun.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import com.example.testtinkerlib.PublicPracticalMethodFromKT
import com.tencent.tinker.lib.library.TinkerLoadLibrary
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals
import com.universal.aifun.ConfigConstants
import com.universal.aifun.LogUtils
import com.universal.aifun.R
import kotlinx.android.synthetic.main.activity_test_tinker.*


class TestTinkerActivity : BaseActivity(), TestTinkerView {

    private val mPresenter: TestTinkerPresenter by lazy { TestTinkerPresenter(this, this) }

    override fun onBeforeSetContentLayout() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_test_tinker
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initUiView() {
    }

    override fun initData() {
    }

    override fun initListener() {
        tv_load.setOnClickListener {
            mPresenter.loadPath()
        }
        tv_clear.setOnClickListener {
            mPresenter.clearPath()
        }
        tv_toast.setOnClickListener {
            tinkerLoadJni(this@TestTinkerActivity)
            LogUtils.i(ConfigConstants.TAG_ALL, PublicPracticalMethodFromKT.ppmfKT.testUpdate())
        }
        tv_show.setOnClickListener {
            mPresenter.showInfo()
        }

        tv_killself.setOnClickListener {
            ShareTinkerInternals.killAllOtherProcess(this@TestTinkerActivity)
            android.os.Process.killProcess(android.os.Process.myPid())
        }


    }

    override fun showLoading() {
        LogUtils.i(ConfigConstants.TAG_ALL, "开始加载")
    }

    override fun hideLoading() {
        LogUtils.i(ConfigConstants.TAG_ALL, "停止加载")
    }

    fun tinkerLoadJni(mContext: Context) {
        LogUtils.i(ConfigConstants.TAG_ALL, "手机的cpu =-= ${getSupportedabis()}")
        TinkerLoadLibrary.installNavitveLibraryABI(mContext, getSupportedabis())
    }

    /**
     * 获取手机的cpu型号
     */
    fun getSupportedabis(): String {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Build.CPU_ABI
        } else {
            Build.SUPPORTED_ABIS[0]
        }
    }
}
