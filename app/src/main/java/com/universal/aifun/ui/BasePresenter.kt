package com.universal.aifun.ui

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.universal.aifun.HandlerUtils
import com.universal.aifun.PermissionsChecker

/**
 * Date:2021/10/26
 * Time:15:39
 * author:joker
 * 基础Presenter
 */
open class BasePresenter<T : BaseView>(private var mContext: Context, private var mView: T) {

    //handler
    protected var mHandler: Handler? = null

    /**
     * 需要用到handler时候，初始化Handler
     */
    fun initHandler(onReceiveMessageListener: HandlerUtils.OnReceiveMessageListener) {
        mHandler = HandlerUtils.HandlerHolder(Looper.myLooper(), mContext, onReceiveMessageListener)
    }

    /**
     * 清除handler
     */
    fun clearHandler() {
        mHandler?.let {
            it.removeCallbacksAndMessages(null)
        }
    }


    /**
     * 判断是否都需要申请权限
     */
    fun isApplyPermission(array: (Array<String>), result: (Boolean) -> Unit) {
        //判断是否有权限需要申请
        val mPermissionsChecker = PermissionsChecker(mContext)
        if (mPermissionsChecker.lacksPermissions(array)) {//去申请权限
            result(true)
        } else {//权限已经有了
            result(false)
        }
    }


}