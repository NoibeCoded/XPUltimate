package me.noibecoded.xpultimate.commands;

import me.noibecoded.xpultimate.XpUltimate;
import me.noibecoded.xpultimate.utils.ResourcePackManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class XpUltimateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            showHelp(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "resourcepack":
            case "rp":
                return handleResourcePack(player);
            case "status":
            case "check":
                return handleStatus(player);
            case "reload":
                return handleReload(player, sender);
            case "help":
            case "?":
                showHelp(player);
                return true;
            default:
                player.sendMessage("§cUnknown command. Use /xpultimate help.");
                return true;
        }
    }

    private void showHelp(Player player) {
        player.sendMessage("§a--- XpUltimate Commands ---");
        player.sendMessage("§e/xpultimate resourcepack §7- Request the resource pack");
        player.sendMessage("§e/xpultimate status §7- Check your resource pack status");
        if (player.hasPermission("xpultimate.reload")) {
            player.sendMessage("§e/xpultimate reload §7- Reload plugin configuration (admin)");
        }
    }

    private boolean handleResourcePack(Player player) {
        if (!XpUltimate.getInstance().isResourcePackSupported()) {
            player.sendMessage("§cResource packs are not supported on this server version.");
            return true;
        }

        String resourcePackUrl = XpUltimate.getInstance().getConfigManager().getResourcePackUrl();

        if (resourcePackUrl == null || resourcePackUrl.isEmpty()) {
            player.sendMessage("§cNo resource pack is configured on this server.");
            return true;
        }

        boolean sent = XpUltimate.getInstance().sendResourcePack(player);

        if (!sent) {
            player.sendMessage("§cFailed to send resource pack. Check console for errors.");
        }

        return true;
    }

    private boolean handleStatus(Player player) {
        ResourcePackManager.PackStatus status = ResourcePackManager.getPlayerPackStatus(player);
        String statusMessage = ResourcePackManager.getStatusMessage(player);

        player.sendMessage("§a--- Resource Pack Status ---");
        player.sendMessage(statusMessage);

        if (status == ResourcePackManager.PackStatus.DECLINED) {
            player.sendMessage("§7");
            player.sendMessage("§eUse §a/xpultimate resourcepack §eto download again.");
        } else if (status == ResourcePackManager.PackStatus.FAILED) {
            player.sendMessage("§7");
            player.sendMessage("§eUse §a/xpultimate resourcepack §eto retry.");
        }

        return true;
    }

    private boolean handleReload(Player player, CommandSender sender) {
        if (!player.hasPermission("xpultimate.reload")) {
            player.sendMessage("§cYou don't have permission to reload the plugin.");
            return true;
        }

        try {
            XpUltimate.getInstance().getConfigManager().reloadConfig();
            XpUltimate.getInstance().getXpDataManager().loadData();

            sender.sendMessage("§aXpUltimate configuration reloaded successfully!");
            return true;
        } catch (Exception e) {
            sender.sendMessage("§cFailed to reload configuration: " + e.getMessage());
            return true;
        }
    }
}
