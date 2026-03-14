package dev.ngb.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {

    public static String maskEmail(String email) {
        if (email == null || email.length() < 3) return "***";
        int at = email.indexOf('@');
        if (at <= 0) return "***";
        return email.substring(0, Math.min(2, at)) + "***" + email.substring(at);
    }
}
