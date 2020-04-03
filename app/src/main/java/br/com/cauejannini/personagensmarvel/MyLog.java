package br.com.cauejannini.personagensmarvel;

import android.util.Log;

public class MyLog {

    public static void e (String tag, String message) {
        if (tag != null && message != null && BuildConfig.DEBUG)
            Log.e(tag, message);
    }

    public static void i (String tag, String message) {
        if (tag != null && message != null && BuildConfig.DEBUG)
            Log.i(tag, message);
    }

    public static void d (String tag, String message) {
        if (tag != null && message != null && BuildConfig.DEBUG)
            Log.d(tag, message);
    }
}
