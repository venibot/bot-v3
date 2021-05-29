package tk.veni.slashCommands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import tk.veni.api.handlers.slashCommands.Command;
import tk.veni.api.handlers.slashCommands.SlashCommand;
import tk.veni.api.models.embeds.InfoEmbeds;

@SlashCommand(name = "ping")
public class PingSlashCommand implements Command {

    @Override
    public void doCommand(SlashCommandEvent event) throws Exception {
        event.replyEmbeds(InfoEmbeds.botLatency(event.getJDA())).queue();
    }

}
