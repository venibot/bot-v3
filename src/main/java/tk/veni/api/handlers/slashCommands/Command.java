package tk.veni.api.handlers.slashCommands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public interface Command {

    void doCommand(SlashCommandEvent event) throws Exception;

    default SlashCommand getCommandData() {
        return getClass().getAnnotation(SlashCommand.class);
    }

}
