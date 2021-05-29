package tk.veni.api.handlers.commands;

import tk.veni.api.models.embeds.ErrorEmbeds;
import tk.veni.api.utils.PermissionsHandler;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {

    private static List<Command> commands = new ArrayList<>();

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

        if (PermissionsHandler.checkAccessLevel(context.getAuthor().getUser(), cd.accessLevel())) {
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
            context.sendMessage(ErrorEmbeds.missingAccessLevel(cd.accessLevel()));
        }
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
