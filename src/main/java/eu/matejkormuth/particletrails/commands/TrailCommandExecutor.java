/**
 * particletrails - ${project.description}
 * Copyright (c) ${project.inceptionYear}, Matej Kormuth <http://www.github.com/dobrakmato>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package eu.matejkormuth.particletrails.commands;

import com.darkblade12.particleeffect.ParticleEffect;
import com.google.common.base.Joiner;
import eu.matejkormuth.particletrails.ParticleChooserGui;
import eu.matejkormuth.particletrails.ParticleTrails;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrailCommandExecutor implements CommandExecutor {

    private final ParticleTrails plugin;
    private final ParticleChooserGui gui = new ParticleChooserGui();

    public TrailCommandExecutor(ParticleTrails particleTrails) {
        this.plugin = particleTrails;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("particletrails.trail")) {
            sender.sendMessage(ChatColor.RED + "You don't have enough permissions!");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is only for players!");
            return true;
        }

        if (args.length == 0) {
            ((Player) sender).openInventory(gui);
        } else if (args.length == 1) {
            String particleName = args[0];

            if (particleName.equalsIgnoreCase("none")) {
                plugin.getActiveEffects().set(((Player) sender).getUniqueId().toString(), null);
                plugin.getActiveEffects().save();
                sender.sendMessage(ChatColor.GREEN + "Your trail particle effect has been removed!");
                return true;
            }

            ParticleEffect desiredPe = null;

            for (ParticleEffect pe : ParticleEffect.values()) {
                if (pe.getName().equalsIgnoreCase(particleName)) {
                    desiredPe = pe;
                }
            }

            if (desiredPe == null) {
                sender.sendMessage(ChatColor.RED + "Invalid particle name!");
                sender.sendMessage(ChatColor.RED + "Supported particle names: " + ChatColor.WHITE + "none, " +
                        Joiner.on(", ").join(ParticleEffect.values()));
            } else {
                plugin.getActiveEffects().set(((Player) sender).getUniqueId().toString(), desiredPe.getName());
                plugin.getActiveEffects().save();

                sender.sendMessage(ChatColor.GREEN + "Your new particle effect is: " + desiredPe.getName());
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Invalid usage! Usage: /trail [particle_name]");
        }

        return true;
    }
}
