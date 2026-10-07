package com.gmstudio.gmmythicevent.listener;

import com.gmstudio.gmmythicevent.mechanic.SoloPullMechanic;
import com.gmstudio.gmmythicevent.mechanic.SoloPushMechanic;
import com.gmstudio.gmmythicevent.mechanic.SoloThrowMechanic;
import com.gmstudio.gmmythicevent.mechanic.SoloVelocityMechanic;
import com.gmstudio.gmmythicevent.condition.GmIsImmuneCondition;
import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.mythic.bukkit.events.MythicConditionLoadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MechanicListener implements Listener {

    @EventHandler
    public void onMechanicLoad(MythicMechanicLoadEvent event) {
        String mechanicName = event.getMechanicName().toLowerCase();
        
        if (mechanicName.equals("solo_pull")) {
            event.register(new SoloPullMechanic(event.getConfig()));
        } else if (mechanicName.equals("solo_push")) {
            event.register(new SoloPushMechanic(event.getConfig()));
        } else if (mechanicName.equals("solo_throw")) {
            event.register(new SoloThrowMechanic(event.getConfig()));
        } else if (mechanicName.equals("solo_velocity")) {
            event.register(new SoloVelocityMechanic(event.getConfig()));
        }
    }

    @EventHandler
    public void onConditionLoad(MythicConditionLoadEvent event) {
        if (event.getConditionName().equalsIgnoreCase("gm_is_immune")) {
            event.register(new GmIsImmuneCondition(event.getConfig()));
        }
    }
}
