package com.gmstudio.gmmythicevent.mechanic;

import com.gmstudio.gmmythicevent.GMMythicEvent;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.Optional;

public class SoloThrowMechanic implements ITargetedEntitySkill {

    private final double velocity;
    private final double velocityY;

    public SoloThrowMechanic(MythicLineConfig config) {
        this.velocity = config.getDouble(new String[]{"velocity", "v"}, 1.0D);
        this.velocityY = config.getDouble(new String[]{"velocityY", "vy"}, 1.0D);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        Entity bukkitTarget = target.getBukkitEntity();
        
        if (bukkitTarget == null) return SkillResult.CONDITION_FAILED;

        Optional<ActiveMob> mythicMob = MythicBukkit.inst().getMobManager().getActiveMob(bukkitTarget.getUniqueId());
        if (mythicMob.isPresent()) {
            String mobType = mythicMob.get().getType().getInternalName();
            if (GMMythicEvent.getInstance().getImmuneMobs().contains(mobType)) {
                return SkillResult.SUCCESS;
            }
        }

        Entity bukkitCaster = data.getCaster().getEntity().getBukkitEntity();
        if (bukkitCaster == null) return SkillResult.CONDITION_FAILED;

        Location targetLoc = bukkitTarget.getLocation();
        Location casterLoc = bukkitCaster.getLocation();

        Vector direction = targetLoc.toVector().subtract(casterLoc.toVector());
        direction.setY(0);
        
        if (direction.lengthSquared() > 0) {
            direction = direction.normalize().multiply(this.velocity / 10.0D);
        } else {
            direction = casterLoc.getDirection().setY(0).normalize().multiply(this.velocity / 10.0D);
        }
        
        direction.setY(this.velocityY / 10.0D);
        bukkitTarget.setVelocity(direction);

        return SkillResult.SUCCESS;
    }
}
