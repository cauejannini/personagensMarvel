package br.com.cauejannini.personagensmarvel;

import android.content.Context;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class Utils {

    public static void showToast(Context context, String text) {
        if (context != null)
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static String currentTimestamp() {
        return String.valueOf(Calendar.getInstance().getTimeInMillis());
    }

    public static String md5Hash(String string) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(string.getBytes());

            byte[] byteArray = digest.digest();

            StringBuilder hex = new StringBuilder();
            for (byte oneByte : byteArray) {
                String hexValue = Integer.toHexString(0xFF & oneByte);
                while (hexValue.length() < 2)
                    hexValue = "0" + hexValue;
                hex.append(hexValue);
            }
            return hex.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }
}
