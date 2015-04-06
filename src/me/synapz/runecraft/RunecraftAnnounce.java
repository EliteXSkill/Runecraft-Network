package me.synapz.runecraft;


import com.connorlinfoot.titleapi.TitleAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RunecraftAnnounce implements CommandExecutor{

    Runecraft rc;

    public RunecraftAnnounce(Runecraft runecraft) {
        rc = runecraft;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("announce")) {
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
    
    private String getAnnounceSuffix() {
        return ChatColor.translateAlternateColorCodes('&', rc.getConfig().getString("announcement-suffix"));
    }
}
