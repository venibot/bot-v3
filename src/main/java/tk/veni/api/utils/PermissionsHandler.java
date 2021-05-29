package tk.veni.api.utils;

import net.dv8tion.jda.api.entities.User;
import tk.veni.api.SupportServer;

public class PermissionsHandler {

    public static boolean checkAccessLevel(User user, short needAccessLevel) {
        if (needAccessLevel == 0) {
            return true;
        } else if (needAccessLevel == 1) {
            return SupportServer.isMember(user);
        } else if (needAccessLevel == 2) {
            return SupportServer.isTester(user);
        } else if (needAccessLevel == 3) {
            return SupportServer.isSupport(user);
        } else if (needAccessLevel == 4) {
            return SupportServer.isDeveloper(user);
        } else if (needAccessLevel == 5) {
            return SupportServer.isOwner(user);
        } else {
            return false;
        }
    }

}
