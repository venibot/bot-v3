package tk.veni.api.models.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import tk.veni.VeniBot;
import tk.veni.api.handlers.commands.BotCommand;
import tk.veni.api.handlers.commands.Command;
import tk.veni.api.utils.DataFormatter;

public class InfoEmbeds {

    private static EmbedBuilder basicInfoEmbed() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(VeniBot.config.colors.get("infoColor"));
        return builder;
    }

    public static MessageEmbed commandHelp(Command command) {
        EmbedBuilder builder = basicInfoEmbed();
        BotCommand cd = command.getCommandData();
        builder.setTitle("Помощь по команде " + cd.name());
        builder.setDescription(cd.description());
        builder.setFooter("<> - обязательный аргумент, [] - необязательный");
        builder.addField("Алиасы(другие варианты написания команды)",
                cd.aliases().length != 0 ? String.join(", ", cd.aliases()).replaceAll(", $", "") : "Отсутствуют",
                true);
        builder.addField("Категория", cd.group(), true);
        builder.addField("Использование", cd.name() + " " + cd.usage(), false);
        builder.addField("Необходимые вам права", DataFormatter.getPermissions(cd.userPermissions()), false);
        builder.addField("Необходимые боту права", DataFormatter.getPermissions(cd.botPermissions()), false);
        return builder.build();
    }

    public static EmbedBuilder commandsHelp() {
        EmbedBuilder builder = basicInfoEmbed();
        builder.setTitle("Помощь по командам бота");
        builder.setDescription("При возникновении проблем с ботом обращайтесь на [сервер поддержки](https://discord.gg/uEhrZUX)");
        builder.setFooter("Зачёркнутые команды вы или бот не можете использовать | <> - обязательный аргумент, [] - необязательный");
        return builder;
    }

    public static MessageEmbed botLatency(JDA bot) {
        EmbedBuilder builder = basicInfoEmbed();
        builder.addField("Задержка до вебсокета", bot.getGatewayPing() + " мс", false);
        return builder.build();
    }

}
