package tk.veni.slashCommands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import tk.veni.api.handlers.slashCommands.Command;
import tk.veni.api.handlers.slashCommands.SlashCommand;

@SlashCommand(name = "say")
public class SaySlashCommand implements Command {

    @Override
    public void doCommand(SlashCommandEvent event) throws Exception {
        event.getChannel().sendMessage(event.getOption("text").getAsString()).queue();
        event.reply("Успешно").setEphemeral(true).queue();
    }
}
