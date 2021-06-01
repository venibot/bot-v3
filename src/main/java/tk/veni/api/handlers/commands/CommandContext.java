package tk.veni.api.handlers.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.atomic.AtomicReference;

public class CommandContext {

    private final MessageReceivedEvent msgEvent;

    private final String usedPrefix;

    private Command command;

    private final String trigger;

    private String[] arguments;

    public CommandContext(MessageReceivedEvent msgEvent, String usedPrefix, String trigger) {
        this.msgEvent = msgEvent;
        this.usedPrefix = usedPrefix;
        this.trigger = trigger;
    }

    public Member getAuthor() {
        return msgEvent.getMember();
    }

    public Message getMessage() {
        return msgEvent.getMessage();
    }

    public MessageChannel getChannel() {
        return msgEvent.getChannel();
    }

    public Guild getGuild() {
        return msgEvent.getGuild();
    }

    public JDA getJDA() {
        return msgEvent.getJDA();
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getUsedPrefix() {
        return usedPrefix;
    }

    public String getTrigger() {
        return trigger;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public Message sendMessage(CharSequence text) {
        AtomicReference<Message> result = new AtomicReference<>();
        this.getMessage().reply(text).queue(result::set);
        return result.get();
    }

    public Message sendMessage(MessageEmbed embed) {
        AtomicReference<Message> result = new AtomicReference<>();
        this.getMessage().reply(embed).queue(result::set);
        return result.get();
    }


}
