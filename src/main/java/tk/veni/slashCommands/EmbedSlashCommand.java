package tk.veni.slashCommands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import tk.veni.VeniBot;
import tk.veni.api.handlers.slashCommands.Command;
import tk.veni.api.handlers.slashCommands.SlashCommand;

@SlashCommand(name = "embed")
public class EmbedSlashCommand implements Command {

    @Override
    public void doCommand(SlashCommandEvent event) throws Exception {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(event.getOption("title").getAsString());
        builder.setDescription(event.getOption("description").getAsString());
        if (event.getOption("color") != null) {
            try {
                builder.setColor(Integer.decode("0x" + event.getOption("color").getAsString().replaceFirst("#", "")));
            } catch (NumberFormatException e) {
                builder.setColor(VeniBot.config.colors.get("infoColor"));
            }
        } else {
            builder.setColor(VeniBot.config.colors.get("infoColor"));
        }
        event.getChannel().sendMessage(builder.build()).queue();
        event.reply("Успешно").setEphemeral(true).queue();
    }
}
