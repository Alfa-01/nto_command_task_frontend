package ru.myitschool.work.utils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class Utils {

    public static int visibleOrGone(boolean isVisible) {
        return isVisible ? VISIBLE : GONE;
    }
}
