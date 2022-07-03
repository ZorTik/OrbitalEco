package me.zort.orbitaleco.commandExecutor;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import me.zort.orbitaleco.OrbitalEco;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class EarnExecutor implements CommandExecutor {

    private final OrbitalEco plugin = OrbitalEco.get();
    private final LoadingCache<String, Long> timeCache = CacheBuilder.newBuilder()
            .maximumSize(5000)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Long>() {
                @Override
                public Long load(String key) throws Exception {
                    return System.currentTimeMillis();
                }
            });

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Long lastUsed = timeCache.getIfPresent(sender.getName());
        long duration;
        if(lastUsed != null && TimeUnit.MILLISECONDS.toMinutes(duration = (System.currentTimeMillis() - lastUsed)) < 1) {
            sender.sendMessage(plugin.getMessage("please-wait", 60 - TimeUnit.MILLISECONDS.toSeconds(duration)));
            return true;
        }
        timeCache.refresh(sender.getName());
        int random = ThreadLocalRandom.current().nextInt(1, 6);
        plugin.modifyBalance(sender.getName(), random);
        sender.sendMessage(plugin.getMessage("earned-money", random));
        return true;
    }
}
