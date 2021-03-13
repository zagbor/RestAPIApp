package zagbor.practice.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static boolean isNumber(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Matcher getMatcher(String s, String reg) {
        Pattern pattern = Pattern.compile(reg);
        return pattern.matcher(s);

    }
}
