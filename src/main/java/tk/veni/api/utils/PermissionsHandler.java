package tk.veni.api.utils;

import tk.veni.api.SupportServer;
import tk.veni.api.handlers.commands.CommandContext;

public class PermissionsHandler {

    public static boolean checkAccessLevel(CommandContext context, short needAccessLevel) {
        if (needAccessLevel == 0) {
            return true;
        } else if (needAccessLevel == 1) {
            return SupportServer.isMember(context.getAuthor().getUser());
        } else if (needAccessLevel == 2) {
            return SupportServer.isTester(context.getAuthor().getUser());
        } else if (needAccessLevel == 3) {
            return SupportServer.isSupport(context.getAuthor().getUser());
        } else if (needAccessLevel == 4) {
            return SupportServer.isDeveloper(context.getAuthor().getUser());
        } else if (needAccessLevel == 5) {
            return SupportServer.isOwner(context.getAuthor().getUser());
        } else {
            return false;
        }
    }

}
