package tk.veni.api.handlers.slashCommands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import tk.veni.api.handlers.commands.CommandHandler;
import tk.veni.api.models.embeds.ErrorEmbeds;
import tk.veni.api.utils.PermissionsHandler;

import java.util.ArrayList;
import java.util.List;

public class SlashCommandHandler {

    private static List<Command> commands = new ArrayList<>();

    public static void addCommand(Command command) {
        SlashCommandHandler.commands.add(command);
    }

    public static List<Command> getCommands() {
        return commands;
    }

    public static Command findCommand(String trigger) {
        for (Command command: SlashCommandHandler.commands) {
            SlashCommand cd = command.getCommandData();
            if (cd.name().equals(trigger.toLowerCase())) {
                return command;
            }
        }
        return null;
    }

    public static void doCommand(Command command, SlashCommandEvent event) {
        SlashCommand cd = command.getCommandData();

        if (cd == null) return;

        if (PermissionsHandler.checkAccessLevel(event.getUser(), cd.accessLevel())) {
            if (event.getMember().hasPermission(cd.userPermissions())) {
                try {
                    command.doCommand(event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                event.replyEmbeds(ErrorEmbeds.missingMemberPermissions(cd.userPermissions())).queue();
            }
        } else {
            event.replyEmbeds(ErrorEmbeds.missingAccessLevel(cd.accessLevel())).queue();
        }
    }

}
