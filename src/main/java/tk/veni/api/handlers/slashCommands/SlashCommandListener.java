package tk.veni.api.handlers.slashCommands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class SlashCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        Command command = SlashCommandHandler.findCommand(event.getName());
        if (command != null) {
            SlashCommandHandler.doCommand(command, event);
        } else {
            event.reply("Указанная вами команда не активирована. Пожалуйста, сообщите об этом разработчику").queue();
        }
    }
}
