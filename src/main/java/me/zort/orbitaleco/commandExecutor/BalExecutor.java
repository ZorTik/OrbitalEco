package me.zort.orbitaleco.commandExecutor;

import me.zort.orbitaleco.OrbitalEco;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BalExecutor implements CommandExecutor {

    private final OrbitalEco plugin = OrbitalEco.get();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length > 0) {
            String player = args[0];
            sender.sendMessage(plugin.getMessage("balance", player, plugin.getBalance(player)));
        } else {
            sender.sendMessage(plugin.getMessage("invalid-args", "/bal <player>"));
        }
        return true;
    }

}
