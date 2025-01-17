/*------------------------------------------------------------------------------
 -   Adapt is a Skill/Integration plugin  for Minecraft Bukkit Servers
 -   Copyright (c) 2022 Arcane Arts (Volmit Software)
 -
 -   This program is free software: you can redistribute it and/or modify
 -   it under the terms of the GNU General Public License as published by
 -   the Free Software Foundation, either version 3 of the License, or
 -   (at your option) any later version.
 -
 -   This program is distributed in the hope that it will be useful,
 -   but WITHOUT ANY WARRANTY; without even the implied warranty of
 -   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 -   GNU General Public License for more details.
 -
 -   You should have received a copy of the GNU General Public License
 -   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 -----------------------------------------------------------------------------*/

package com.volmit.adapt.content.adaptation.seaborrne;

import com.volmit.adapt.api.adaptation.SimpleAdaptation;
import com.volmit.adapt.util.C;
import com.volmit.adapt.util.Element;
import com.volmit.adapt.util.Localizer;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SeaborneSpeed extends SimpleAdaptation<SeaborneSpeed.Config> {

    public SeaborneSpeed() {
        super("seaborne-speed");
        registerConfiguration(Config.class);
        setDescription(Localizer.dLocalize("seaborn", "dolphingrace", "description"));
        setDisplayName(Localizer.dLocalize("seaborn", "dolphingrace", "name"));
        setIcon(Material.TRIDENT);
        setBaseCost(getConfig().baseCost);
        setMaxLevel(getConfig().maxLevel);
        setInterval(1020);
        setInitialCost(getConfig().initialCost);
        setCostFactor(getConfig().costFactor);
    }

    @Override
    public void addStats(int level, Element v) {
        v.addLore(C.GRAY + Localizer.dLocalize("seaborn", "dolphingrace", "lore1") + C.GREEN + (level) + C.GRAY + Localizer.dLocalize("seaborn", "dolphingrace", "lore2"));
        v.addLore(C.ITALIC + Localizer.dLocalize("seaborn", "dolphingrace", "lore3"));
    }

    @EventHandler
    public void on(EntityAirChangeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (p.getInventory().getBoots() != null && p.getInventory().getBoots().getEnchantments().containsKey(Enchantment.DEPTH_STRIDER)) {
                return;
            }
        }
        if (e.getEntity() instanceof Player p && p.isSwimming() && hasAdaptation(p) && p.getWorld().getBlockAt(p.getLocation()).isLiquid()) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 50, getLevel(p)));
            p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, getLevel(p), getLevel(p)));
        }
    }

    @Override
    public void onTick() {

    }

    @Override
    public boolean isEnabled() {
        return getConfig().enabled;
    }

    @Override
    public boolean isPermanent() {
        return getConfig().permanent;
    }

    @NoArgsConstructor
    protected static class Config {
        boolean permanent = false;
        boolean enabled = true;
        int baseCost = 3;
        int maxLevel = 7;
        int initialCost = 2;
        double costFactor = 0.525;
    }
}
