package tk.veni.api.handlers.slashCommands;

import net.dv8tion.jda.api.Permission;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SlashCommand {

    String name();

    Permission[] botPermissions() default {Permission.MESSAGE_EMBED_LINKS};

    Permission[] userPermissions() default {Permission.MESSAGE_WRITE};

    short accessLevel() default 0;

}
