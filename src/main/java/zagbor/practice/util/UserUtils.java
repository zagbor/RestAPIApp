package zagbor.practice.util;

import javax.servlet.http.HttpServletRequest;

public class UserUtils {
    public static boolean requestIsValid(HttpServletRequest req) {
        final String name = req.getParameter("name");
        final String role = req.getParameter("role");
        return name != null && name.length() > 0
                && role != null && role.length() > 0;
    }
}
