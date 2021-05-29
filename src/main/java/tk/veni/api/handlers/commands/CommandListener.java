package tk.veni.api.handlers.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import tk.veni.VeniBot;
import tk.veni.api.models.Config;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Config config = VeniBot.config;
        if (event.getAuthor().isBot()) return;
        if (event.getMessage().isMentioned(event.getJDA().getSelfUser())) {
            String truncated = "";
            String firstMention = "<@" + event.getJDA().getSelfUser().getIdLong() + ">";
            String secondMention = "<@!" + event.getJDA().getSelfUser().getIdLong() + ">";
            if (event.getMessage().getContentRaw().startsWith(firstMention)) {
                truncated = event.getMessage().getContentRaw()
                        .replaceFirst(firstMention, "").trim();
            } else if (event.getMessage().getContentRaw().startsWith(secondMention)) {
                truncated = event.getMessage().getContentRaw()
                        .replaceFirst(firstMention, "").trim();
            }
            if (!truncated.equals("")) {
                String commandName = truncated.split(" ")[0];
                Command command = CommandHandler.findCommand(commandName);

                if (command != null) {
                    CommandContext context = new CommandContext(event,
                            event.getJDA().getSelfUser().getAsMention(), commandName);
                    context.setCommand(command);
                    CommandHandler.doCommand(command, context,
                            truncated.replaceFirst(commandName, ""));
                }
            }
        }
        else if (event.getMessage().getContentRaw().startsWith(config.defaultPrefix)) {
            String truncated = event.getMessage().getContentRaw()
                    .replaceFirst(config.defaultPrefix, "").trim();
            String commandName = truncated.split(" ")[0];
            Command command = CommandHandler.findCommand(commandName);

            if (command != null) {
                CommandContext context = new CommandContext(event,
                        event.getJDA().getSelfUser().getAsMention(), commandName);
                context.setCommand(command);
                CommandHandler.doCommand(command, context,
                        truncated.replaceFirst(commandName, ""));
            }
        }
    }
}
