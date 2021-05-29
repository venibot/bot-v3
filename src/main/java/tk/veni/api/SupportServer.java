package tk.veni.api;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import tk.veni.VeniBot;

import java.util.List;

public class SupportServer {

    private static Guild getGuild(User user) {
        return user.getJDA().getGuildById(VeniBot.config.supportServerIds.get("guildId"));
    }

    private static boolean isAlwaysOwner(User user) {
        return VeniBot.config.ownersIds.contains(user.getIdLong());
    }

    public static boolean isMember(User user) {
        return getGuild(user).isMember(user) || isAlwaysOwner(user);
    }

    public static boolean isTester(User user) {
        if (isMember(user)) {
            return getGuild(user).getMember(user).getRoles()
                    .contains(getGuild(user).getRoleById(VeniBot.config.supportServerIds.get("testerRoleId")))
                    || isAlwaysOwner(user);
        }
        return false;
    }

    public static boolean isSupport(User user) {
        if (isMember(user)) {
            return getGuild(user).getMember(user).getRoles()
                    .contains(getGuild(user).getRoleById(VeniBot.config.supportServerIds.get("supportRoleId")))
                    || isAlwaysOwner(user);
        }
        return false;
    }
    public static boolean isDeveloper(User user) {
        if (isMember(user)) {
            return getGuild(user).getMember(user).getRoles()
                    .contains(getGuild(user).getRoleById(VeniBot.config.supportServerIds.get("developerRoleId")))
                    || isAlwaysOwner(user);
        }
        return false;
    }
    public static boolean isOwner(User user) {
        if (isMember(user)) {
            return getGuild(user).getMember(user).getRoles()
                    .contains(getGuild(user).getRoleById(VeniBot.config.supportServerIds.get("ownerRoleId")))
                    || isAlwaysOwner(user);
        }
        return false;
    }

}
