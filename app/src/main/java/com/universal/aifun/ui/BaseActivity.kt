package com.universal.aifun.ui

import android.os.Bundle
import android.view.Window
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity

/**
 * Date:2021/10/26
 * Time:15:45
 * author:joker
 * 基础类
 */
abstract class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //1.禁止横竖屏切换
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //2.关闭屏幕顶部的标题
        window.requestFeature(Window.FEATURE_NO_TITLE)
        //3.在加载布局之前做的时候
        onBeforeSetContentLayout()
        //4.加载xml布局
        setContentView(getLayoutId())

        initUiView()
        initData()
        initListener()
    }

    /**
     * 在设置视图内容之前
     * 需要做什么操作可以写在该方法中
     */
    protected abstract fun onBeforeSetContentLayout()

    /**
     * 得到xml布局文件的id
     */
    protected abstract fun getLayoutId(): Int


    /**
     * 初始化UI控件
     */
    protected abstract fun initUiView()

    /**
     * 初始化数据
     */
    protected abstract fun initData()

    /**
     * 初始化监听
     */
    protected abstract fun initListener()

    override fun onResume() {
        super.onResume()
    }


    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}