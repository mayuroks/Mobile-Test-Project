package project.test.mobile.utils;

/**
 * Created by Mayur on 18-10-2017.
 */

public class TextUtils {
    public static boolean isValidString(String str) {
        if (str != null && str.length() > 0 &&
                !str.isEmpty() && !str.equalsIgnoreCase("")) {
            return true;
        }

        return false;
    }
}
