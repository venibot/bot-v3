package tk.veni.api.models.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import tk.veni.VeniBot;
import tk.veni.api.utils.DataFormatter;

public class ErrorEmbeds {

    private static EmbedBuilder basicErrorEmbed() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(VeniBot.config.colors.get("errorColor"));
        return builder;
    }

    public static MessageEmbed missingMemberPermissions(Permission[] permissions) {
        EmbedBuilder builder = basicErrorEmbed();
        builder.setTitle("У вас недостаточно прав для выполнения данной команды");
        builder.setDescription("Для выполнения данной команды вам необходимы следующие права:\n"
                + DataFormatter.getPermissions(permissions));
        return builder.build();
    }

    public static MessageEmbed missingBotPermissions(Permission[] permissions) {
        EmbedBuilder builder = basicErrorEmbed();
        builder.setTitle("У бота недостаточно прав для выполнения данной команды");
        builder.setDescription("Для выполнения данной команды боту необходимы следующие права:\n"
                + DataFormatter.getPermissions(permissions));
        return builder.build();
    }

    public static MessageEmbed missingAccessLevel(short accessLevel) {
        EmbedBuilder builder = basicErrorEmbed();
        builder.setTitle("У вас недостаточный уровень доступа для выполнения данной команды");
        builder.setDescription("Для выполнения данной команды вам необходим уровень доступа " + accessLevel + ":\n"
                + DataFormatter.getMissingAccessLevel(accessLevel));
        return builder.build();
    }

}
