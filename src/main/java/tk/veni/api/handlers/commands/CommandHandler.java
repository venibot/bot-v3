package tk.veni.api.handlers.commands;

import tk.veni.api.models.embeds.ErrorEmbeds;
import tk.veni.api.utils.DataFormatter;
import tk.veni.api.utils.PermissionsHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandHandler {

    public static List<Command> commands = new ArrayList<>();

    public static void addCommand(Command command) {
        CommandHandler.commands.add(command);
    }

    public static Command findCommand(String trigger) {
        for (Command command: CommandHandler.commands) {
            BotCommand cd = command.getCommandData();
            if (cd.name().equals(trigger.toLowerCase())) {
                return command;
            } else if (cd.aliases().length != 0) {
                for (String alias: cd.aliases()) {
                    if (alias.equals(trigger.toLowerCase())) {
                        return command;
                    }
                }
            }
        }
        return null;
    }

    public static void doCommand(Command command, CommandContext context, String args) {
        BotCommand cd = command.getCommandData();

        if (cd == null) return;

        if (PermissionsHandler.checkAccessLevel(context, cd.accessLevel())) {
            if (context.getAuthor().hasPermission(cd.userPermissions())) {
                String[] arguments = getArguments(command, args);
                try {
                    command.doCommand(context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                context.sendMessage(ErrorEmbeds.missingMemberPermissions(cd.userPermissions()));
            }
        } else {
            context.getChannel().sendMessage(ErrorEmbeds.missingAccessLevel(cd.accessLevel())).queue();
        }
    }

    public static void findAndRun(String trigger, CommandContext context, String arguments) {
        Command command = CommandHandler.findCommand(trigger);

        if (command == null || command.getCommandData() == null) return;
        context.setCommand(command);
        CommandHandler.doCommand(command, context, arguments);
    }

    private static String[] getArguments(Command command, String args) {
        String[] arguments;
        if (args.equals("")) {
            arguments = new String[0];
        } else {
            if (command.getCommandData().arguments() == 0 || command.getCommandData().arguments() == 1) {
                arguments = new String[1];
                arguments[0] = args.replace("^[ ]*", "").trim();
            } else {
                arguments = args.replace("^[ ]*", "").trim()
                        .split(" ", command.getCommandData().arguments());
            }
        }
        return arguments;
    }

}
