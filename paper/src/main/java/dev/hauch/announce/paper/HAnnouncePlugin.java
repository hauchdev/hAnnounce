package dev.hauch.announce.paper;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Plugin entry point of hAnnounce.
 *
 * <p>This class is deliberately minimal: it provides a valid, loadable plugin shell that the
 * product wiring will be attached to as features are implemented. It does not implement any
 * announcement functionality, commands, schedulers, or configuration handling.</p>
 */
public final class HAnnouncePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("hAnnounce loaded (foundation build - no features enabled).");
    }
}
