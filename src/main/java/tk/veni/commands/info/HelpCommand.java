package tk.veni.commands.info;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import tk.veni.api.SupportServer;
import tk.veni.api.handlers.commands.BotCommand;
import tk.veni.api.handlers.commands.Command;
import tk.veni.api.handlers.commands.CommandContext;
import tk.veni.api.handlers.commands.CommandHandler;
import tk.veni.api.models.embeds.ErrorEmbeds;
import tk.veni.api.models.embeds.InfoEmbeds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@BotCommand(name = "help", description = "Помощь по командам бота", aliases = {"хелп"}, usage = "[Команда]",
        group = "Информация", arguments = 1)
public class HelpCommand implements Command {

    @Override
    public void doCommand(CommandContext context) throws Exception {
        if (context.getArguments().length == 0) {
            EmbedBuilder builder = InfoEmbeds.commandsHelp();
            HashMap<String, List<Command>> commands = new HashMap<>();
            for (Command command: CommandHandler.getCommands()) {
                BotCommand cd = command.getCommandData();
                if (cd.hidden()) continue;
                if (!commands.containsKey(cd.group())) commands.put(cd.group(), new ArrayList<>());
                commands.get(cd.group()).add(command);
            }
            for (String group: commands.keySet()) {
                String commandsGroup = "";
                for (Command command: commands.get(group)) {
                    if (context.getGuild().getSelfMember()
                            .hasPermission(command.getCommandData().botPermissions()) &&
                        context.getAuthor().hasPermission(command.getCommandData().userPermissions())) {
                        // If user and bot have permissions to run command
                        commandsGroup += command.getCommandData().name();
                    } else {
                        // If user or bot has no permissions to run command
                        commandsGroup += "~~" + command.getCommandData().name() + "~~";
                    }
                }
                builder.addField(group, commandsGroup, false);
            }
            context.sendMessage(builder.build());
        } else {
            Command command = CommandHandler.findCommand(context.getArguments()[0]);
            if (command != null) {
                if (command.getCommandData().hidden() &&
                        !SupportServer.isDeveloper(context.getAuthor().getUser())) {
                    context.sendMessage(":no_entry: Указанная вами команда не найдена");
                    return;
                }
                command.showHelp(context);
            } else {
                context.sendMessage(":no_entry: Указанная вами команда не найдена");
            }
        }
    }
}
