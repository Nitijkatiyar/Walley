package com.tecbeast.hdwallpapers.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author iRESLab
 */
public class Preferences {

    static SharedPreferences audio_manager_preference;
    public static final String KEY_DEFAULT_PREFERENCE = "AudioManagerPrefs";


    public static final String AM_IS_REGISTERED = "pin_registered";
    public static final String AM_LOGIN_PIN = "login_Pin";
    public static final String AM_RECOVERY_EMAIL_ADDRESS = "recovery_email";


    public static SharedPreferences getDefaultPref(Context context) {
        if (Preferences.audio_manager_preference == null) {
            Preferences.audio_manager_preference = context.getApplicationContext().getSharedPreferences(
                    KEY_DEFAULT_PREFERENCE, Context.MODE_PRIVATE);
        }
        return Preferences.audio_manager_preference;
    }


    public static void clearpreferences(Context context) {
        Editor editor = getDefaultPref(context).edit();
        editor.clear();
        editor.commit();
    }

    public static void setBoolean(Context context, String key, boolean value) {
        Editor editor = getDefaultPref(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key) {
        return getDefaultPref(context).getBoolean(key, false);
    }


    public static void setString(Context context, String key, String value) {

        Editor editor = getDefaultPref(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        return getDefaultPref(context).getString(key, "");
    }


}
