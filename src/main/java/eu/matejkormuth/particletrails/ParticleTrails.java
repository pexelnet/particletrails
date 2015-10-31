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
package eu.matejkormuth.particletrails;

import eu.matejkormuth.particletrails.commands.TrailCommandExecutor;
import eu.matejkormuth.particletrails.listeners.ChooserGuiListener;
import eu.matejkormuth.particletrails.listeners.ParticleSpawnListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class ParticleTrails extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");

    private ConfigurationFile activeEffects = new ConfigurationFile(
            new File(this.getDataFolder().getAbsolutePath() + "./active_effects.yml"));

    @Override
    public void onEnable() {
        log.info("Loading " + getDescription().getFullName() + " by " + getDescription().getAuthors().get(0));
        log.info("Check my other plugins: https://www.spigotmc.org/resources/authors/dobrakmato.16389/");

        saveDefaultConfig();

        getCommand("trail").setExecutor(new TrailCommandExecutor(this));

        Bukkit.getPluginManager().registerEvents(new ParticleSpawnListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ChooserGuiListener(this), this);
    }

    public ConfigurationFile getActiveEffects() {
        return activeEffects;
    }

    @Override
    public void onDisable() {

    }
}
