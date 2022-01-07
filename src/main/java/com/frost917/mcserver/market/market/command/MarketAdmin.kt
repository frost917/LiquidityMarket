package com.frost917.mcserver.market.market.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

class MarketAdmin: TabExecutor {
    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender Source of the command.  For players tab-completing a
     * command inside of a command block, this will be the player, not
     * the command block.
     * @param command Command which was executed
     * @param alias The alias used
     * @param args The arguments passed to the command, including final
     * partial argument to be completed and command label
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String>? {
        TODO("Not yet implemented")
    }

    /**
     * Executes the given command, returning its success.
     * <br></br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return true if a valid command, otherwise false
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        TODO("Not yet implemented")
    }
}