package com.ei.desktop.utils;

import java.util.prefs.Preferences;

/**
 * @author yitiansong
 * @since 2024/8/11
 * 用来获取和设置用户的偏好设置
 */

public class PreferenceUtils {
    private static final Preferences PREFERENCES = Preferences.userNodeForPackage(PreferenceUtils.class);

    public static String get(String key) {
        return PREFERENCES.get(key, "");
    }

    public static void put(String key, String value) {
        PREFERENCES.put(key, value);
    }

    public static void remove(String token) {
        // remove token from preferences
        PREFERENCES.remove(token);
    }
}
