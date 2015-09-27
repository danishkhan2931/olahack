package com.ola.olafriends.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by thecodegame on 13-09-2015.
 */
public class Debug {

    private static final boolean DEBUG = true;
    private static final String TAG = "olafriends";

    private Debug() {
    }

    private static String getMsg(String msg) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
        String className = stackTraceElement.getClassName();
        String methodName = stackTraceElement.getMethodName();
        int line = stackTraceElement.getLineNumber();
        msg = className + "::" + methodName + "() [" + line + "]" + " : " + msg;
        return msg;
    }

    public static void check() {
        if (DEBUG) {
            Log.e(TAG, getMsg("CHECK"));
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Log.e(TAG, getMsg(msg));
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            Log.d(TAG, getMsg(msg));
        }
    }

    public static void i(String msg) {
        if (DEBUG) {
            Log.i(TAG, getMsg(msg));
        }
    }

    public static void w(String msg) {
        if (DEBUG) {
            Log.w(TAG, getMsg(msg));
        }
    }

    public static void v(String msg) {
        if (DEBUG) {
            Log.v(TAG, getMsg(msg));
        }
    }

    public static void toastShort(String msg, Context context, boolean user) {
        if (DEBUG || user) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void toastLong(String msg, Context context, boolean user) {
        if (DEBUG || user) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }

    private static String debugObject(String key, Object obj) {
        if (obj == null) {
            return String.format("%s = <null>%n", key);
        }
        return String.format("%s = %s (%s)%n", key, String.valueOf(obj), obj.getClass()
                .getSimpleName());
    }

    public static void bundle(Bundle bundle) {
        if (DEBUG && bundle != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bundle{\n");
            for (String key : bundle.keySet()) {
                sb.append(debugObject(key, bundle.get(key)));
            }
            sb.append("}");
            Log.i(TAG, getMsg(sb.toString()));
        }
    }

    public static void intent(Intent intent){
        if(DEBUG && intent != null){
            bundle(intent.getExtras());
        }
    }

    public static void preferences(SharedPreferences prefs) {
        if (DEBUG && prefs != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("SharedPreferences{\n");
            for (Map.Entry<String, ?> entry : prefs.getAll().entrySet()) {
                sb.append(debugObject(entry.getKey(), entry.getValue()));
            }
            sb.append("}");
            Log.i(TAG, getMsg(sb.toString()));
        }
    }

    public static void JSONObject(JSONObject jsonObject) {
        if (DEBUG && jsonObject != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("JsonObject{\n");
            Iterator<String> s = jsonObject.keys();
            while(s.hasNext()){
                String key = s.next();
                try {
                    sb.append(debugObject(key, jsonObject.get(key)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            sb.append("}");
            Log.i(TAG, getMsg(sb.toString()));
        }
    }
}

