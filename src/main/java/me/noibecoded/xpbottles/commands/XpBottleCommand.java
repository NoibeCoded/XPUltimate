package me.noibecoded.xpbottles.commands;

import me.noibecoded.xpbottles.XpBottles;
import me.noibecoded.xpbottles.data.XpDataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class XpBottleCommand implements CommandExecutor {

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
            case "create":
            case "c":
                return handleCreate(player, args);
            case "deposit":
            case "d":
                return handleDeposit(player, args);
            case "withdraw":
            case "w":
                return handleWithdraw(player, args);
            case "transfer":
            case "t":
            case "transferir":
                return handleTransfer(player, args);
            case "balance":
            case "b":
                return handleBalance(player);
            case "reset":
            case "r":
                return handleReset(player, args);
            default:
                player.sendMessage("§cUnknown command. Use /xpbottle for help.");
                return true;
        }
    }

    private void showHelp(Player player) {
        player.sendMessage("§a--- XpBottles ---");
        player.sendMessage("§e/xpbottle create <amount> §7- Create XP bottle from your XP");
        player.sendMessage("§e/xpbottle deposit <amount> §7- Deposit XP to bank");
        player.sendMessage("§e/xpbottle withdraw <amount> §7- Withdraw XP from bank");
        player.sendMessage("§e/xpbottle transfer <player> <amount> §7- Transfer XP to player");
        player.sendMessage("§e/xpbottle balance §7- Check bank balance");
        player.sendMessage("§e/xpbottle reset [player] §7- Reset XP (admin)");
    }

    private boolean handleCreate(Player player, String[] args) {
        if (!player.hasPermission("xpbottles.create")) {
            player.sendMessage("§cYou don't have permission to create XP bottles.");
            return true;
        }

        if (args.length < 2) {
            player.sendMessage("§cUsage: /xpbottle create <amount>");
            return true;
        }

        try {
            int amount = Integer.parseInt(args[1]);
            if (amount <= 0) {
                player.sendMessage("§cAmount must be greater than 0.");
                return true;
            }

            int playerXp = player.getTotalExperience();

            if (playerXp < amount) {
                player.sendMessage("§cYou need at least " + amount + " XP. You have: " + playerXp);
                return true;
            }

            int remainingXp = playerXp - amount;

            player.setTotalExperience(0);
            player.setLevel(0);
            player.setExp(0);

            if (remainingXp > 0) {
                player.giveExp(remainingXp);
            }

            XpDataManager.giveXpBottle(player, amount);

            player.sendMessage("§aCreated XP bottle with " + amount + " XP!");
        } catch (NumberFormatException e) {
            player.sendMessage("§cInvalid amount.");
        }

        return true;
    }

    private boolean handleDeposit(Player player, String[] args) {
        if (!player.hasPermission("xpbottles.deposit")) {
            player.sendMessage("§cYou don't have permission to deposit XP.");
            return true;
        }

        if (args.length < 2) {
            player.sendMessage("§cUsage: /xpbottle deposit <amount>");
            return true;
        }

        try {
            int amount = Integer.parseInt(args[1]);
            if (amount <= 0) {
                player.sendMessage("§cAmount must be greater than 0.");
                return true;
            }

            int playerXp = player.getTotalExperience();

            if (playerXp < amount) {
                player.sendMessage("§cYou need at least " + amount + " XP. You have: " + playerXp);
                return true;
            }

            int remainingXp = playerXp - amount;

            player.setTotalExperience(0);
            player.setLevel(0);
            player.setExp(0);

            if (remainingXp > 0) {
                player.giveExp(remainingXp);
            }

            XpDataManager.addXp(player, amount);

            player.sendMessage("§aDeposited " + amount + " XP to bank! Balance: " + XpDataManager.getTotalXp(player));
        } catch (NumberFormatException e) {
            player.sendMessage("§cInvalid amount.");
        }

        return true;
    }

    private boolean handleWithdraw(Player player, String[] args) {
        if (!player.hasPermission("xpbottles.withdraw")) {
            player.sendMessage("§cYou don't have permission to withdraw XP.");
            return true;
        }

        if (args.length < 2) {
            player.sendMessage("§cUsage: /xpbottle withdraw <amount>");
            return true;
        }

        try {
            int amount = Integer.parseInt(args[1]);
            if (amount <= 0) {
                player.sendMessage("§cAmount must be greater than 0.");
                return true;
            }

            int bankXp = XpDataManager.getTotalXp(player);

            if (bankXp < amount) {
                player.sendMessage("§cYou only have " + bankXp + " XP in bank.");
                return true;
            }

            XpDataManager.removeXp(player, amount);
            player.giveExp(amount);

            player.sendMessage("§aWithdrew " + amount + " XP from bank! Balance: " + XpDataManager.getTotalXp(player));
        } catch (NumberFormatException e) {
            player.sendMessage("§cInvalid amount.");
        }

        return true;
    }

    private boolean handleTransfer(Player player, String[] args) {
        if (!player.hasPermission("xpbottles.transfer")) {
            player.sendMessage("§cYou don't have permission to transfer XP.");
            return true;
        }

        if (args.length < 3) {
            player.sendMessage("§cUsage: /xpbottle transfer <player> <amount>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage("§cPlayer not found.");
            return true;
        }

        if (target.equals(player)) {
            player.sendMessage("§cYou can't transfer XP to yourself.");
            return true;
        }

        try {
            int amount = Integer.parseInt(args[2]);
            if (amount <= 0) {
                player.sendMessage("§cAmount must be greater than 0.");
                return true;
            }

            int bankXp = XpDataManager.getTotalXp(player);

            if (bankXp < amount) {
                player.sendMessage("§cYou only have " + bankXp + " XP in bank.");
                return true;
            }

            XpDataManager.removeXp(player, amount);
            XpDataManager.addXp(target, amount);

            player.sendMessage("§aTransferred " + amount + " XP to " + target.getName() + "! Your balance: " + XpDataManager.getTotalXp(player));
            target.sendMessage("§aYou received " + amount + " XP from " + player.getName() + "! Your balance: " + XpDataManager.getTotalXp(target));
        } catch (NumberFormatException e) {
            player.sendMessage("§cInvalid amount.");
        }

        return true;
    }

    private boolean handleBalance(Player player) {
        int bankXp = XpDataManager.getTotalXp(player);
        int playerXp = player.getTotalExperience();
        int playerLevel = player.getLevel();

        player.sendMessage("§a--- XP Balance ---");
        player.sendMessage("§7Bank XP: §e" + bankXp);
        player.sendMessage("§7Your XP: §e" + playerXp + " §7(Level: " + playerLevel + ")");
        return true;
    }

    private boolean handleReset(Player player, String[] args) {
        if (!player.hasPermission("xpbottles.reset")) {
            player.sendMessage("§cYou don't have permission to reset XP.");
            return true;
        }

        Player target;
        if (args.length >= 2) {
            if (!player.hasPermission("xpbottles.reset.others")) {
                player.sendMessage("§cYou can't reset other players' XP.");
                return true;
            }
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage("§cPlayer not found.");
                return true;
            }
        } else {
            target = player;
        }

        int previousBankXp = XpDataManager.getTotalXp(target);
        XpDataManager.setXp(target, 0);

        player.sendMessage("§aReset XP bank for " + target.getName() + " (was: " + previousBankXp + " XP)");
        if (!target.equals(player)) {
            target.sendMessage("§cYour XP bank has been reset by an admin.");
        }

        return true;
    }
}
