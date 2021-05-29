package tk.veni.api.utils;

import net.dv8tion.jda.api.Permission;
import tk.veni.VeniBot;

import java.util.HashMap;

public class DataFormatter {

    public static String getPermissions(Permission[] permissions) {
        String permissionsString = "";
        HashMap<String, String> permissionsTranslates = VeniBot.config.permissionsTranslates;

        for (Permission permission: permissions) {
            permissionsString += permissionsTranslates.get(permission.toString()) + ", ";
        }
        return permissionsString.replaceAll(", $", "");
    }

    public static String getMissingAccessLevel(short accessLevel) {
        if (accessLevel == 0) {
            return "пользователь бота";
        } else if (accessLevel == 1) {
            return "участник сервера поддержки";
        } else if (accessLevel == 2) {
            return "тестер бота";
        } else if (accessLevel == 3) {
            return "поддержка бота";
        } else if (accessLevel == 4) {
            return "разработчик бота";
        } else if (accessLevel == 5) {
            return "создатель бота";
        } else {
            return "мой создатель допустил ошибку и указал неверную циферку, пожалуйста, сообщите ему об этой ошибке";
        }
    }

}
