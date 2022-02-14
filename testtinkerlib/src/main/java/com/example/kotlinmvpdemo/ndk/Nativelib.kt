package com.example.kotlinmvpdemo.ndk

import android.content.Context

/**
 * Date:2022/2/11
 * Time:14:44
 * author:dimple
 */
class Nativelib {

    init {
        try {
            System.loadLibrary("nativehaha")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    external fun stringFromJNI(): String
}