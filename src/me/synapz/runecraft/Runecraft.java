package me.synapz.runecraft;


import com.connorlinfoot.titleapi.TitleAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Runecraft extends JavaPlugin implements Listener{

    @Override
    public void onEnable()
    {
        getConfig().options().copyDefaults(true);
        saveConfig();

        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {

    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("announce")) {
            // quick permission check
            if (sender instanceof Player && !permissionCheck((Player) sender, "runecraft.announce")) {
                return true;
            }

            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Wrong usage!");
                sender.sendMessage(ChatColor.RED + "Usage: /announce <message>");
            }
            else if (args.length >= 1) {
                // create a message using past args
                String message = messageBuilder(args);

                // send the message to everyone on the server
                for (Player player : Bukkit.getOnlinePlayers()) {
                    TitleAPI.sendTitle(player, 20, 100, 20, getAnnounceSuffix(), ChatColor.GRAY + message);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1);
                }
                Bukkit.broadcastMessage(getAnnounceSuffix() + ChatColor.GRAY + message);

            }
        }
        return false;
    }

    private boolean permissionCheck(Player player, String permission) {
        if (player.hasPermission(permission)) {
            return true;
        } else {
            player.sendMessage(ChatColor.RED + "You don't have access to that command!");
            return false;
        }
    }

    private String messageBuilder(String[] args) {
        String msg1 = " ";
        for (int i = 0; i < args.length; i++) {
            msg1 = msg1 + args[i] + " ";
        }

        return msg1;
    }

    private String getWelcomeMessage() {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("welcome-subtitle"));
    }

    private String getWelcomeTitle() {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("welcome-title"));
    }

    private String getAnnounceSuffix() {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("announcement-suffix"));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        String welcomeTitle = getWelcomeTitle();
        String welcomeSubtitle = getWelcomeMessage();

        TitleAPI.sendTitle(player, 20, 100, 20, welcomeTitle, welcomeSubtitle);
    }


}

