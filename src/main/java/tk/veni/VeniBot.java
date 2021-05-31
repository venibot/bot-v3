package tk.veni;

import com.google.gson.Gson;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandUpdateAction;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import tk.veni.api.handlers.commands.Command;
import tk.veni.api.handlers.commands.CommandHandler;
import tk.veni.api.handlers.commands.CommandListener;
import tk.veni.api.handlers.slashCommands.SlashCommandHandler;
import tk.veni.api.handlers.slashCommands.SlashCommandListener;
import tk.veni.api.models.Config;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class VeniBot {

    public static Config config;

    public static void main(String[] args) throws Exception {
        VeniBot.config = loadConfig();
        if (config == null) {
            throw new Exception("Configuration load error");
        }
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(config.botToken);
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setActivity(Activity.playing("создание 3 версии себя"));
        ShardManager bot = builder.build();
        if (!loadEvents(bot, "src/main/java/tk/veni/events", "tk.veni.events")) {
            throw new Exception("Events load error");
        }
        if (!loadCommands("src/main/java/tk/veni/commands", "tk.veni.commands")) {
            throw new Exception("Commands load error");
        }
        if (!initSlashCommands(bot)) {
            throw new Exception("Slash commands init error");
        }
        if (!loadSlashCommands("src/main/java/tk/veni/slashCommands", "tk.veni.slashCommands")) {
            throw new Exception("Slash commands load error");
        }
        bot.addEventListener(new CommandListener());
        bot.addEventListener(new SlashCommandListener());
    }

    private static Config loadConfig() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("config.json")));
            Config config = new Gson().fromJson(content, Config.class);
            return config;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean loadEvents(ShardManager bot, String path, String eventsPackage) {
        try {
            eventsPackage += ".";
            File dir = new File(path);
            for (File file : dir.listFiles()) {
                if (file.isFile() && file.getName().endsWith("Event.java")) {
                    Class event_class = Class.forName(eventsPackage + file.getName().replace(".java", ""));
                    ListenerAdapter event = (ListenerAdapter) event_class.newInstance();
                    bot.addEventListener(event);
                } else if (file.isDirectory()) {
                    loadEvents(bot, file.getPath(), eventsPackage + file.getName());
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean loadCommands(String path, String commandsPackage) {
        try {
            commandsPackage += ".";
            File dir = new File(path);
            for (File file : dir.listFiles()) {
                if (file.isFile() && file.getName().endsWith("Command.java")) {
                    Class cmd = Class.forName(commandsPackage + file.getName().replace(".java", ""));
                    try {
                        Command command = (Command) cmd.newInstance();
                        CommandHandler.addCommand(command);
                    } catch (ClassCastException e) {
                        System.out.println(cmd.getName() + " не является командой, пропускаю");
                        continue;
                    }
                } else if (file.isDirectory()) {
                    loadCommands(file.getPath(), commandsPackage + file.getName());
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean initSlashCommands(ShardManager bot) {
        for (JDA jda: bot.getShards()) {
            CommandUpdateAction commands = jda.updateCommands();

            commands.addCommands(new CommandData("ping", "Получение информации о задержке бота"));
            commands.addCommands(new CommandData("say", "Вывод текста от лица бота")
                    .addOptions(new OptionData(OptionType.STRING, "text",
                            "Текст, который необходимо вывести", true)));
            commands.addCommands(new CommandData("embed", "Вывод embed сообщения от лица бота")
                    .addOptions(new OptionData(OptionType.STRING, "title", "Заголовок", true),
                            new OptionData(OptionType.STRING, "description", "Описание", true),
                            new OptionData(OptionType.STRING, "color", "Цвет embed сообщения(в формате HEX)")));

            commands.queue();
        }
        return true;
    }

    private static boolean loadSlashCommands(String path, String commandsPackage) {
        try {
            commandsPackage += ".";
            File dir = new File(path);
            for (File file : dir.listFiles()) {
                if (file.isFile() && file.getName().endsWith("SlashCommand.java")) {
                    Class cmd = Class.forName(commandsPackage + file.getName().replace(".java", ""));
                    try {
                        tk.veni.api.handlers.slashCommands.Command command = (tk.veni.api.handlers.slashCommands.Command) cmd.newInstance();
                        SlashCommandHandler.addCommand(command);
                    } catch (ClassCastException e) {
                        System.out.println(cmd.getName() + " не является слэш командой, пропускаю");
                        continue;
                    }
                } else if (file.isDirectory()) {
                    loadSlashCommands(file.getPath(), commandsPackage + file.getName());
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
