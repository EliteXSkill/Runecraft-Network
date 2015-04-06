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

        RunecraftAnnounce an = new RunecraftAnnounce(this);
        getCommand("announce").setExecutor(an);

    }

    @Override
    public void onDisable() {

    }

    private String getWelcomeMessage() {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("welcome-subtitle"));
    }

    private String getWelcomeTitle() {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("welcome-title"));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        String welcomeTitle = getWelcomeTitle();
        String welcomeSubtitle = getWelcomeMessage();

        TitleAPI.sendTitle(player, 20, 100, 20, welcomeTitle, welcomeSubtitle);
    }


}

