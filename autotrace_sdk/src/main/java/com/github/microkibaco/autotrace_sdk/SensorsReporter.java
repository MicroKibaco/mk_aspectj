package com.github.microkibaco.autotrace_sdk;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;

import androidx.annotation.Keep;
import androidx.annotation.RequiresApi;

/**
 * @author 杨正友(小木箱)于 2020/10/8 11 52 创建
 * @Email: yzy569015640@gmail.com
 * @Tel: 18390833563
 * @function description:
 */
@Keep
public class SensorsReporter {

    private final String TAG = this.getClass().getSimpleName();

    public static final String SDK_VERSION = "1.0.0";
    private volatile static SensorsReporter SENSORS_DATA_API_INSTANCE;
    private static Map<String, Object> mDeviceInfo;
    private String mDeviceId;

    public SensorsReporter(Application application) {
        mDeviceId = SensorsDataManager.getAndroidId(application);
        mDeviceInfo = SensorsDataManager.getDeviceInfo(application);

    }


    /**
     * Trace 事件
     *
     * @param eventName  事件名称
     * @param properties 设备信息
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void track(String eventName, JSONObject properties) {

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event", eventName);
            jsonObject.put("device_id", mDeviceId);
            final JSONObject sendProperties = new JSONObject(mDeviceInfo);

            if (Objects.nonNull(sendProperties)) {
                SensorsDataManager.mergeJsonObject(properties, sendProperties);
            }
            jsonObject.put("properties", sendProperties);
            jsonObject.put("time", System.currentTimeMillis());
            Log.i(TAG, SensorsDataManager.formatJson(jsonObject.toString()));
        } catch (JSONException e) {
            Log.getStackTraceString(e);
        }
    }
}
