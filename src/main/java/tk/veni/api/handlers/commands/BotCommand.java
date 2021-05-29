package tk.veni.api.handlers.commands;

import net.dv8tion.jda.api.Permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BotCommand {

    String name();

    String description() default "Не указано";

    String[] aliases() default {};

    String usage() default "";

    String group() default "Другое";

    Permission[] botPermissions() default {Permission.MESSAGE_EMBED_LINKS};

    Permission[] userPermissions() default {Permission.MESSAGE_WRITE};

    short arguments() default 0;

    boolean hidden() default false;

    short accessLevel() default 0;

}
