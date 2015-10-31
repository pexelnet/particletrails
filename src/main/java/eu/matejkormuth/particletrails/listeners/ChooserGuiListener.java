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
package eu.matejkormuth.particletrails.listeners;

import eu.matejkormuth.particletrails.ParticleChooserGui;
import eu.matejkormuth.particletrails.ParticleTrails;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ChooserGuiListener implements Listener {
    private final ParticleTrails plugin;

    public ChooserGuiListener(ParticleTrails particleTrails) {
        this.plugin = particleTrails;
    }

    @EventHandler
    private void onInventoryClick(final InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getClickedInventory().getTitle().equals(ParticleChooserGui.INV_TITLE)) {
            ItemStack clicked = event.getCurrentItem();
            if (clicked != null) {
                String particleEffectName = clicked.getItemMeta().getDisplayName();

                // Execute command.
                plugin.getServer().dispatchCommand(event.getWhoClicked(), "trail " + particleEffectName);
            }
            event.setCancelled(true);

            // Close GUI.
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> event.getWhoClicked().closeInventory());
        }
    }
}
