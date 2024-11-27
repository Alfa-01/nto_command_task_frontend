package ru.myitschool.work.utils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.SharedPreferences;


public class Utils {

    public static int visibleOrGone(boolean isVisible) {
        return isVisible ? VISIBLE : GONE;
    }
    public static void saveLogin(String login, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login", login);
        editor.commit();
    }

    public static void deleteLogin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login", null);
        editor.commit();
    }

    public static String getLogin(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "login", Context.MODE_PRIVATE);
        return preferences.getString("login", null);
    }
}
