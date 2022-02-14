package com.universal.aifun;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorRes;

import com.universal.aifun.ui.PermissionsActivity;

import java.util.TimeZone;

/**
 * Date:2021/5/11
 * Time:8:44
 * author:joker
 * 公共实用类
 */
public class PublicPracticalMethodFromJAVA {

    private volatile static PublicPracticalMethodFromJAVA mInstance;

    /**
     * 单例模式
     */
    public static PublicPracticalMethodFromJAVA getInstance() {
        if (mInstance == null) {
            synchronized (PublicPracticalMethodFromJAVA.class) {
                if (mInstance == null) {
                    mInstance = new PublicPracticalMethodFromJAVA();
                }
            }
        }
        return mInstance;
    }


    /**
     * 00001
     * 1.解决app点击桌面图标每次重新启动
     * 2.当是web页面跳转我们app的时候，如果本应用已经在后台，那么直接唤起；如果没有则重新启动
     *
     * @param activity
     */
    public void restartApp(Activity activity) {
        if (!activity.isTaskRoot()) {
            Intent intent = activity.getIntent();
            if (intent != null) {
                String scheme = intent.getScheme();
                if (!StringUtils.isBlank(scheme) && scheme.equals(TKExtManage.getInstance().getAppScheme(activity))) {
                    activity.finish();
                }
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) &&
                        Intent.ACTION_MAIN.equals(action)) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 应用信息界面
     *
     * @param activity
     */
    public void toApplicationInfo(Activity activity) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivity(localIntent);
    }

    /**
     * 系统设置界面
     *
     * @param activity
     */
    public void toSystemConfig(Activity activity) {
        try {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到权限设置
     *
     * @param activity
     */
    public void toPermissionSetting(Activity activity) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            toSystemConfig(activity);
        } else {
            try {
                toApplicationInfo(activity);
            } catch (Exception e) {
                e.printStackTrace();
                toSystemConfig(activity);
            }
        }
    }

    /**
     * 获得手机屏幕信息
     */
    public void getPhoneScreenInfo(Context context) {
        ScreenTools.getInstance().getDisplayAreaInformation(context);
        ScreenTools.getInstance().getEquipmentInformation(context);
    }


    /**
     * 状态栏透明方法
     * - SYSTEM_UI_FLAG_LOW_PROFILE   设置状态栏和导航栏中的图标变小，变模糊或者弱化其效果。这个标志一般用于游戏，电子书，视频，或者不需要去分散用户注意力的应用软件。
     * - SYSTEM_UI_FLAG_HIDE_NAVIGATION  隐藏导航栏，点击屏幕任意区域，导航栏将重新出现，并且不会自动消失
     * - SYSTEM_UI_FLAG_FULLSCREEN       隐藏状态栏，点击屏幕区域不会出现，需要从状态栏位置下拉才会出现
     * - SYSTEM_UI_FLAG_LAYOUT_STABLE    稳定布局，主要是在全屏和非全屏切换时，布局不要有大的变化。一般和View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN、View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION搭配使用。同时，android:fitsSystemWindows要设置为true
     * - SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  将布局内容拓展到导航栏的后面
     * - SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN     将布局内容拓展到状态的后面
     * - SYSTEM_UI_FLAG_IMMERSIVE    使状态栏和导航栏真正的进入沉浸模式,即全屏模式，如果没有设置这个标志，设置全屏时，我们点击屏幕的任意位置，就会恢复为正常模式
     * - SYSTEM_UI_FLAG_IMMERSIVE_STICKY  它的效果跟View.SYSTEM_UI_FLAG_IMMERSIVE一样。但是，它在全屏模式下，用户上下拉状态栏或者导航栏时，这些系统栏只是以半透明的状态显示出来，并且在一定时间后会自动消息
     *
     * @param useThemestatusBarColor 是否使用特殊的标题栏背景颜色，android5.0以上可以设置状态栏背景色，如果不使用则使用透明色值
     * @param useStatusBarColor      是否使用状态栏文字和图标为暗色，如果状态栏采用了白色系，则需要使状态栏和图标为暗色，android6.0以上可以设置
     */
    public void transparentStatusBar(Activity activity,
                                     boolean useThemestatusBarColor,
                                     boolean useStatusBarColor,
                                     @ColorRes int color) {
//        //适配Android 4.4 +的方法
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = activity.getWindow();
//            // Translucent status bar
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // Translucent navigation bar
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//        //适配 Android 5.0+ 的方法
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = activity.getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//        }

        Window window = activity.getWindow();
        //5.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

  /*          window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            View decorView = window.getDecorView();*/
         /*   int options = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            decorView.setSystemUiVisibility(options);*/

//            if (useThemestatusBarColor) {
//                window.setStatusBarColor(activity.getResources().getColor(color));
//            } else {
//                window.setStatusBarColor(Color.TRANSPARENT);
//            }

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = window.getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                    | localLayoutParams.flags);
        }

        //android6.0以后可以对状态栏文字颜色和图标进行修改
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (useStatusBarColor) {//暗色
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {//浅色
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }


    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    /**
     * handler运行
     */
    public void runHandlerFun(Handler handler, int whatcode, long delayMillis) {
        Message message = Message.obtain();
        message.what = whatcode;
        handler.sendMessageDelayed(message, delayMillis);
    }

    /**
     * 页面跳转
     *
     * @param mActivity   上下文
     * @param cls         目标的Activity
     * @param flag        跳转的方式
     * @param bundle      bundle值
     * @param isFinish    进入页面后，是否结束上一个页面
     * @param enterAnimID 进入动画ID
     * @param exitAnimID  退出动画ID
     */
    public void intentToJump(Activity mActivity, Class<?> cls, int flag, Bundle bundle, boolean isFinish, int enterAnimID, int exitAnimID) {
        Intent intent = new Intent(mActivity, cls);
        if (!StringUtils.isBlank(bundle)) {
            intent.putExtras(bundle);
        }
        if (flag != -1) {
            intent.setFlags(flag);
        }

        mActivity.startActivity(intent);

        if (isFinish) {
            mActivity.finish();
        }

        if (!StringUtils.isBlank(enterAnimID) || !StringUtils.isBlank(exitAnimID)) {
            mActivity.overridePendingTransition(enterAnimID, exitAnimID);
        }
    }

    /**
     * Acticity 页面关闭,可以传动动画文件
     *
     * @param mActivity
     * @param exitAnimID
     */
    public void activityFinish(Activity mActivity, int exitAnimID) {
        mActivity.finish();
        mActivity.overridePendingTransition(0, exitAnimID);
    }

    /**
     * 动态设置Shape  RECTANGLE
     */
    public void setDynamicShapeRECTANGLE(Context mContext, View view, float CornerRadius, int strokewidth, String strokeColor, String bgcolor) {
        GradientDrawable drawable = new GradientDrawable();
        //设置shape的形状
        drawable.setShape(GradientDrawable.RECTANGLE);

        //设置shape的圆角度数
        if (!StringUtils.isBlank(CornerRadius) && CornerRadius != -1) {
            drawable.setCornerRadius(CornerRadius);
        }

        //设置shape的边的宽度和颜色
        if (!StringUtils.isBlank(strokewidth) && strokewidth != -1
                && !StringUtils.isBlank(strokeColor)) {
//            drawable.setStroke(strokewidth, ContextCompat.getColor(mContext, R.color.appblack));
            drawable.setStroke(strokewidth, Color.parseColor(strokeColor));
        }

        //设置shape的背景色
        if (!StringUtils.isBlank(bgcolor)) {
//            drawable.setColor(ContextCompat.getColor(mContext, bgcolor));
            drawable.setColor(Color.parseColor(bgcolor));
        }
        view.setBackground(drawable);
    }

    /**
     * 动态设置Shape  OVAL
     */
    public void setDynamicShapeOVAL(Context mContext, View view, int width, String bgcolor) {
        GradientDrawable drawable = new GradientDrawable();
        //设置shape的形状
        drawable.setShape(GradientDrawable.OVAL);

        //设置shape的背景色
        if (!StringUtils.isBlank(bgcolor)) {
            drawable.setColor(Color.parseColor(bgcolor));
        }

        //设置圆的size大小
        if (!StringUtils.isBlank(width) && width != -1) {
            drawable.setSize(width, width);
        }
        view.setBackground(drawable);
    }

    /**
     * 动态设置Shape  OVAL
     */
    public void setDynamicShapeOVAL2(Context mContext, View view, int width, int strokewidth, String strokeColor, String bgcolor) {
        GradientDrawable drawable = new GradientDrawable();
        //设置shape的形状
        drawable.setShape(GradientDrawable.OVAL);


        if (!StringUtils.isBlank(strokewidth) && strokewidth != -1 &&
                !StringUtils.isBlank(strokeColor)) {
            drawable.setStroke(strokewidth, Color.parseColor(strokeColor));
        }
        //设置shape的背景色
        if (!StringUtils.isBlank(bgcolor)) {
            drawable.setColor(Color.parseColor(bgcolor));
        }

        //设置圆的size大小
        if (!StringUtils.isBlank(width) && width != -1) {
            drawable.setSize(width, width);
        }
        view.setBackground(drawable);
    }

    /**
     * 获取当前时区
     */
    public String getCurrentTimeZone() {
//        Calendar cal = Calendar.getInstance();
//        TimeZone tz1 = cal.getTimeZone();
//        LogUtils.i(ConfigConstants.TAG_ALL, "tz1 =-= " + tz);
        String default_timezone = "";
        TimeZone tz = TimeZone.getDefault();
//        default_timezone = tz.getID().replaceAll("\\/", "%2F");
        default_timezone = tz.getID();
//        LogUtils.i(ConfigConstants.TAG_ALL, "default_timezone =-= " + default_timezone);
        return default_timezone;
    }


    /**
     * 获取token的值
     */
    public String getToken(String token) {
        String result_token = "";
        if (token.startsWith("Bearer ")) {
            result_token = token;
        } else {
            result_token = "Bearer " + token;
        }
        return result_token;
    }

    /**
     * 设置屏幕的亮度模式
     * Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC：值为1，自动调节亮度
     * Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL：值为0，手动模式。
     */
    public void setScrennManualMode(Context mContext, boolean isautomatic) {
        try {
            ContentResolver contentResolver = mContext.getContentResolver();
            if (isautomatic) {
                Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
            } else {
                Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            }
            int mode = Settings.System.getInt(contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE);
            LogUtils.i(ConfigConstants.TAG_ALL, "mode =-= " + mode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取屏幕亮度
     * 屏幕最大亮度为255。
     * 屏幕最低亮度为0。
     * 屏幕亮度值范围必须位于：0～255。
     *
     * @return
     */
    public int getScreenBrightness(Context mContext) {
        ContentResolver contentResolver = mContext.getContentResolver();
        int defVal = 125;
        return Settings.System.getInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, defVal);
    }


    /**
     * 设置屏幕亮度
     */
    public void saveScreenBrightness(Context mContext, int value) {
        ContentResolver contentResolver = mContext.getContentResolver();
        Settings.System.putInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, value);
    }







    /**
     * 启动权限管理类
     */
    public void startPermissionsActivity(Activity mActivity, String[] permissions, PermissionsActivity.PermissionsListener permissionsListener, int mark) {
        int[] dailogcontent = new int[]{R.string.quit,
                R.string.settings,
                R.string.help,
                R.string.string_help_text};
        PermissionsActivity.startActivityForResult(mActivity, ConfigConstants.PERMISSIONS_INIT_REQUEST_CODE, dailogcontent, permissions, permissionsListener, mark);
    }








    /**
     * dialog 需要全屏的时候用，和clearFocusNotAle() 成对出现
     * 在show 前调用  focusNotAle   show后调用clearFocusNotAle
     *
     * @param window
     */
    public void focusNotAle(Window window) {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    /**
     * dialog 需要全屏的时候用，focusNotAle() 成对出现
     * 在show 前调用  focusNotAle   show后调用clearFocusNotAle
     *
     * @param window
     */
    public void clearFocusNotAle(Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }


    /**
     * 隐藏虚拟栏 ，显示的时候再隐藏掉
     *
     * @param window
     */
    public void hideNavigationBar(final Window window) {
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        window.getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                int uiOptions =
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                //隐藏导航栏
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                if (Build.VERSION.SDK_INT >= 19) {
                    uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                } else {
                    uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
                }
                window.getDecorView().setSystemUiVisibility(uiOptions);
            }
        });
    }


    /**
     * 判断点击是否在指定的控件内
     *
     * @param views
     * @param x
     * @param y
     * @return
     */
    public boolean isTouchPointInView(View[] views, int x, int y) {
        boolean isInView = false;
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int left = location[0];
            int top = location[1];
            int right = left + view.getMeasuredWidth();
            int bottom = top + view.getMeasuredHeight();
//            LogUtils.i(ConfigConstants.TAG_ALL, "x =-= " + x, "y =-= " + y,
//                    "left =-= " + left,
//                    "right =-= " + right,
//                    "top =-= " + top,
//                    "bottom =-= " + bottom
//            );
            if (x >= left && x <= right
                    && y >= top && y <= bottom) {//点击在指定的控件内
                isInView = true;
                break;
            }
        }
//        LogUtils.i(ConfigConstants.TAG_ALL, "isInView =-= " + isInView);
        return isInView;
    }
}
