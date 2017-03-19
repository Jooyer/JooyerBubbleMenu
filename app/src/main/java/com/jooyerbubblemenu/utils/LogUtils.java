package com.jooyerbubblemenu.utils;


import com.jooyerbubblemenu.BuildConfig;

/** 日志工具类
 * Created by Jooyer on 2016/9/17
 */
public class LogUtils {

    protected static final String TAG = "test";
    //规定每段显示的长度
    private static int LOG_MAXLENGTH = 2000;
    /**
     * Send a VERBOSE log message.
     *
     * @param msg
     *            The message you would like logged.
     */
    public static void v(String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.v(TAG, buildMessage(msg));
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param msg
     *            The message you would like logged.
     * @param thr
     *            An exception to log
     */
    public static void v(String msg, Throwable thr) {
        if (BuildConfig.DEBUG)
            android.util.Log.v(TAG, buildMessage(msg), thr);
    }

    /**
     * @param tag  -->标记
     * @param msg -->内容
     */
    public static void v(String tag,String msg){
        if (BuildConfig.DEBUG){
            android.util.Log.v(tag,"===" + msg);
        }
    }
    /**
     * Send a DEBUG log message.
     *
     * @param msg
     */
    public static void d(String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.d(TAG, buildMessage(msg));
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param msg
     *            The message you would like logged.
     * @param thr
     *            An exception to log
     */
    public static void d(String msg, Throwable thr) {
        if (BuildConfig.DEBUG)
            android.util.Log.d(TAG, buildMessage(msg), thr);
    }

    public static void d(String tag,String msg){
        if (BuildConfig.DEBUG){
            android.util.Log.d(tag,"===" + msg);
        }
    }

    /**
     * Send an INFO log message.
     *
     * @param msg
     *            The message you would like logged.
     */
    public static void i(String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.i(TAG, buildMessage(msg));
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param msg
     *            The message you would like logged.
     * @param thr
     *            An exception to log
     */
    public static void i(String msg, Throwable thr) {
        if (BuildConfig.DEBUG)
            android.util.Log.i(TAG, buildMessage(msg), thr);
    }

    public static void i(String tag,String msg){
        if (BuildConfig.DEBUG){

            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end){
                    android.util.Log.i(tag,"===" + msg.substring(start,end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                }else {
                    android.util.Log.i(tag,"===" + msg.substring(start,strLength));
                    break;
                }
            }
        }
    }

    /**
     * Send an ERROR log message.
     *
     * @param msg
     *            The message you would like logged.
     */
    public static void e(String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.e(TAG, buildMessage(msg));
    }

    /**
     * Send a WARN log message
     *
     * @param msg
     *            The message you would like logged.
     */
    public static void w(String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.w(TAG, buildMessage(msg));
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param msg
     *            The message you would like logged.
     * @param thr
     *            An exception to log
     */
    public static void w(String msg, Throwable thr) {
        if (BuildConfig.DEBUG)
            android.util.Log.w(TAG, buildMessage(msg), thr);
    }

    /**
     * Send an empty WARN log message and log the exception.
     *
     * @param thr
     *            An exception to log
     */
    public static void w(Throwable thr) {
        if (BuildConfig.DEBUG)
            android.util.Log.w(TAG, buildMessage(""), thr);
    }

    /**
     * Send an ERROR log message and log the exception.
     *
     * @param msg
     *            The message you would like logged.
     * @param thr
     *            An exception to log
     */
    public static void e(String msg, Throwable thr) {
        if (BuildConfig.DEBUG)
            android.util.Log.e(TAG, buildMessage(msg), thr);
    }

    /**
     * Building Message
     *
     * @param msg
     *            The message you would like logged.
     * @return Message String
     */
    protected static String buildMessage(String msg) {
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        return new StringBuilder().append(caller.getClassName()).append(".").append(caller.getMethodName()).append("(): ").append(msg).toString();
    }



}
