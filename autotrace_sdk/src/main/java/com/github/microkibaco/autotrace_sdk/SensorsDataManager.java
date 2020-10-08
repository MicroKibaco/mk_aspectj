package com.github.microkibaco.autotrace_sdk;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * @author 杨正友(小木箱)于 2020/10/8 11 55 创建
 * @Email: yzy569015640@gmail.com
 * @Tel: 18390833563
 * @function description:
 */
public class SensorsDataManager {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" + ".SSS", Locale.CHINA);
    public static void mergeJsonObject(final JSONObject source, JSONObject dest) {
        final Iterator<String> superPropertiesIterator = source.keys();
        while (superPropertiesIterator.hasNext()) {
            final String key = superPropertiesIterator.next();
            final Object value;
            try {
                value = source.get(key);
                synchronized (DATE_FORMAT) {
                    dest.put(key, value instanceof Date ? DATE_FORMAT.format((Date) value) : value);
                }
            } catch (JSONException e) {
                Log.getStackTraceString(e);
            }
        }
    }

    public static Map<String, Object> getDeviceInfo(Application application) {
        final Map<String, Object> deviceInfo = new HashMap<>(10);

        deviceInfo.put( "$lib", "Android");
        deviceInfo.put( "$lib_version", SensorsReporter.SDK_VERSION);
        deviceInfo.put("$os", "Android");
        deviceInfo.put("$os_version", Build.VERSION.RELEASE == null ? "UNKNOWN" : Build.VERSION.RELEASE);
        deviceInfo.put( "$manufacturer", Build.MANUFACTURER == null ? "UNKNOWN" : Build.MANUFACTURER);
        deviceInfo.put("$model", TextUtils.isEmpty(Build.MODEL) ? "UNKNOWN" : Build.MODEL.trim());

        try {
            final PackageManager manager = application.getPackageManager();
            final PackageInfo packageInfo = manager.getPackageInfo(application.getPackageName(), 0);
            deviceInfo.put("$app_version", packageInfo.versionName);

            final int labelRes = packageInfo.applicationInfo.labelRes;
            deviceInfo.put("$app_name", application.getResources().getString(labelRes));
        } catch (final Exception e) {
            e.printStackTrace();
        }

        final DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        deviceInfo.put("$screen_height", displayMetrics.heightPixels);
        deviceInfo.put("$screen_width", displayMetrics.widthPixels);

        return Collections.unmodifiableMap(deviceInfo);
    }

    /**
     * 获取 Android ID
     *
     * @param mContext Context
     * @return String
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidId(Context mContext) {
        String androidId = "";
        try {
            androidId = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        return androidId;
    }

    public static String formatJson(String jsonStr) {
        try {
            if (null == jsonStr || "".equals(jsonStr)) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            char last;
            char current = '\0';
            int indent = 0;
            boolean isInQuotationMarks = false;
            for (int i = 0; i < jsonStr.length(); i++) {
                last = current;
                current = jsonStr.charAt(i);
                switch (current) {
                    case '"':
                        if (last != '\\') {
                            isInQuotationMarks = !isInQuotationMarks;
                        }
                        sb.append(current);
                        break;
                    case '{':
                    case '[':
                        sb.append(current);
                        if (!isInQuotationMarks) {
                            sb.append('\n');
                            indent++;
                            addIndentBlank(sb, indent);
                        }
                        break;
                    case '}':
                    case ']':
                        if (!isInQuotationMarks) {
                            sb.append('\n');
                            indent--;
                            addIndentBlank(sb, indent);
                        }
                        sb.append(current);
                        break;
                    case ',':
                        sb.append(current);
                        if (last != '\\' && !isInQuotationMarks) {
                            sb.append('\n');
                            addIndentBlank(sb, indent);
                        }
                        break;
                    default:
                        sb.append(current);
                }
            }

            return sb.toString();
        } catch (Exception e) {
            Log.getStackTraceString(e);
            return "";
        }
    }


    private static void addIndentBlank(StringBuilder sb, int indent) {
        try {
            for (int i = 0; i < indent; i++) {
                sb.append('\t');
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }

}
