package tk.veni.api.handlers.commands;

import tk.veni.api.models.embeds.InfoEmbeds;

public interface Command {

    void doCommand(CommandContext context) throws Exception;

    default BotCommand getCommandData() {
        return getClass().getAnnotation(BotCommand.class);
    }

    default void showHelp(CommandContext context) {
        context.sendMessage(InfoEmbeds.commandHelp(this));
    }

}
