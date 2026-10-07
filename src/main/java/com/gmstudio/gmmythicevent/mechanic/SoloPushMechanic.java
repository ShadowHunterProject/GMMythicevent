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

public class SoloPushMechanic implements ITargetedEntitySkill {

    private final double velocity;

    public SoloPushMechanic(MythicLineConfig config) {
        this.velocity = config.getDouble(new String[]{"velocity", "v"}, 1.0D);
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

        double vel = this.velocity / 10.0;
        double distance = casterLoc.distance(targetLoc);
        double modxz = distance * 0.5 * vel;
        double mody = distance * 0.34 * vel;
        mody = casterLoc.getY() - targetLoc.getY() != 0.0 ? mody * (Math.abs(casterLoc.getY() - targetLoc.getY()) * 0.5) : mody;
        
        Vector v = targetLoc.toVector().subtract(casterLoc.toVector()).normalize().multiply(vel);
        v.setX(v.getX() * modxz);
        v.setZ(v.getZ() * modxz);
        v.setY(v.getY() * mody);
        
        if (v.lengthSquared() > 16.0) { // equivalent to length > 4.0
            v = v.normalize().multiply(4);
        }
        
        if (Double.isNaN(v.getX())) v.setX(0);
        if (Double.isNaN(v.getY())) v.setY(0);
        if (Double.isNaN(v.getZ())) v.setZ(0);
        
        bukkitTarget.setVelocity(v);

        return SkillResult.SUCCESS;
    }
}
