package com.gmstudio.gmmythicevent.mechanic;

import com.gmstudio.gmmythicevent.GMMythicEvent;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.Optional;

public class SoloVelocityMechanic implements ITargetedEntitySkill {

    private final String mode;
    private final double x;
    private final double y;
    private final double z;

    public SoloVelocityMechanic(MythicLineConfig config) {
        this.mode = config.getString(new String[]{"mode", "m"}, "add").toLowerCase();
        this.x = config.getDouble(new String[]{"x"}, 0.0D);
        this.y = config.getDouble(new String[]{"y"}, 0.0D);
        this.z = config.getDouble(new String[]{"z"}, 0.0D);
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

        Vector vel = new Vector(this.x, this.y, this.z);
        if (this.mode.equals("set")) {
            bukkitTarget.setVelocity(vel);
        } else {
            bukkitTarget.setVelocity(bukkitTarget.getVelocity().add(vel));
        }

        return SkillResult.SUCCESS;
    }
}
