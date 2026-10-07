import io.lumine.mythic.core.skills.mechanics.PullMechanic;
import io.lumine.mythic.core.skills.mechanics.PushMechanic;
import io.lumine.mythic.core.skills.mechanics.ThrowMechanic;
import io.lumine.mythic.core.skills.mechanics.VelocityMechanic;
import io.lumine.mythic.api.config.MythicLineConfig;

public class test {
    public test(MythicLineConfig mlc) {
        Object pull = new PullMechanic(null, "pull", mlc);
        Object push = new PushMechanic(null, "push", mlc);
        Object thr = new ThrowMechanic(null, "throw", mlc);
        Object vel = new VelocityMechanic(null, "velocity", mlc);
    }
}
