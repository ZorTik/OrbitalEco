package me.zort.orbitaleco.commandExecutor;

import me.zort.orbitaleco.OrbitalEco;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetBalExecutor implements CommandExecutor {

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
            plugin.setBalance(player, amount);
            sender.sendMessage(plugin.getMessage("set-money", amount, player));
        } else {
            sender.sendMessage(plugin.getMessage("invalid-args", "/setbal <player> <amount>"));
        }
        return true;
    }

}
