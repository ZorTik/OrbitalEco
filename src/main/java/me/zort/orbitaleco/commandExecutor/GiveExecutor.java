package me.zort.orbitaleco.commandExecutor;

import me.zort.orbitaleco.OrbitalEco;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveExecutor implements CommandExecutor {

    private final OrbitalEco plugin = OrbitalEco.get();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length > 1) {
            String player = args[0];
            double amount = Double.parseDouble(args[1]);
            if(amount <= 0) {
                sender.sendMessage(plugin.getMessage("negative-money"));
                return true;
            }
            if(plugin.getBalance(sender.getName()) < amount) {
                sender.sendMessage(plugin.getMessage("not-enough-money"));
                return true;
            } else {
                plugin.modifyBalance(sender.getName(), -amount);
                sender.sendMessage(plugin.getMessage("given-money", amount, player));
            }
            plugin.modifyBalance(player, amount);
            Player target = Bukkit.getPlayer(player);
            if(target != null) {
                target.sendMessage(plugin.getMessage("received-money", amount, sender.getName()));
            }
        } else {
            sender.sendMessage(plugin.getMessage("invalid-args", "/give <player> <amount>"));
        }
        return true;
    }

}
