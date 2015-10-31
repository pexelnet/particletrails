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

import com.darkblade12.particleeffect.ParticleEffect;
import eu.matejkormuth.particletrails.ParticleTrails;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ParticleSpawnListener implements Listener {

    private final ParticleTrails plugin;
    private final int baseCount;
    private final double range;
    private final boolean onlyOnGround;

    public ParticleSpawnListener(ParticleTrails particleTrails) {
        this.plugin = particleTrails;

        this.baseCount = plugin.getConfig().getInt("trail.base_amount", 20);
        this.range = plugin.getConfig().getDouble("trail.range", Double.MAX_VALUE);
        this.onlyOnGround = plugin.getConfig().getBoolean("trail.only_on_ground", false);
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    private void onPlayerMove(final PlayerMoveEvent event) {
        String uuid = event.getPlayer().getUniqueId().toString();
        if (plugin.getActiveEffects().contains(uuid)) {
            if (onlyOnGround && !event.getPlayer().isOnGround()) {
                return;
            }

            double speed = event.getTo().distanceSquared(event.getFrom());
            int count = (int) (speed * baseCount);

            ParticleEffect pe = ParticleEffect.fromName(plugin.getActiveEffects().getString(uuid));
            try {
                pe.display(0.5f, 0.15f, 0.5f, 0, count, event.getPlayer().getLocation(), range);
            } catch (Exception e) {
                // Just don't spam console.
            }
        }
    }
}
