package tk.veni;

import com.google.gson.Gson;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import tk.veni.api.handlers.commands.Command;
import tk.veni.api.handlers.commands.CommandHandler;
import tk.veni.api.handlers.commands.CommandListener;
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
        bot.addEventListener(new CommandListener());
    }

    public static Config loadConfig() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("config.json")));
            Config config = new Gson().fromJson(content, Config.class);
            return config;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean loadEvents(ShardManager bot, String path, String eventsPackage) {
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

    public static boolean loadCommands(String path, String commands_package) {
        try {
            commands_package += ".";
            File dir = new File(path);
            for (File file : dir.listFiles()) {
                if (file.isFile() && file.getName().endsWith("Command.java")) {
                    Class cmd = Class.forName(commands_package + file.getName().replace(".java", ""));
                    try {
                        Command command = (Command) cmd.newInstance();
                        CommandHandler.addCommand(command);
                    } catch (ClassCastException e) {
                        System.out.println(cmd.getName() + " не является командой, пропускаю");
                        continue;
                    }
                } else if (file.isDirectory()) {
                    loadCommands(file.getPath(), commands_package + file.getName());
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
