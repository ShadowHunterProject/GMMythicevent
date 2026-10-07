package com.gmstudio.gmmythicevent.condition;

import com.gmstudio.gmmythicevent.GMMythicEvent;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.conditions.IEntityComparisonCondition;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.entity.Entity;
import java.util.Optional;

public class GmIsImmuneCondition implements IEntityComparisonCondition {

    public GmIsImmuneCondition(MythicLineConfig config) {
    }

    @Override
    public boolean check(AbstractEntity caster, AbstractEntity target) {
        Entity t = target.getBukkitEntity();
        if (t == null) return false;

        Optional<ActiveMob> activeMobOpt = MythicBukkit.inst().getMobManager().getActiveMob(t.getUniqueId());
        if (activeMobOpt.isPresent()) {
            String mobType = activeMobOpt.get().getType().getInternalName();
            if (GMMythicEvent.getInstance().getImmuneMobs().contains(mobType)) {
                return true;
            }
        }
        return false;
    }
}
