package com.example.testtinkerlib

import android.content.Context
import com.example.kotlinmvpdemo.ndk.Nativelib

/**
 * Date:2022/2/10
 * Time:14:14
 * author:dimple
 */
class PublicPracticalMethodFromKT {

    companion object {
        val ppmfKT: PublicPracticalMethodFromKT by
        lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            PublicPracticalMethodFromKT()
        }
    }

    /**
     * 测试更新前方法
     */
    fun testUpdate(): String {
//        val result = "使用Tinker更新前的内容，=-= 原始内容"

        var result = "使用Tinker更新后的内容，=-= 被更新了，1.0.1"
        val nativelib = Nativelib()
        result = "$result  添加了so文件方法---${nativelib.stringFromJNI()}"

//        val result = "使用Tinker更新后的内容，=-= 被更新了，1.0.2"

        return result
    }
}