package me.suicidalkids.antimidairabuse;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class AntiMidairAbuse extends JavaPlugin implements Listener {

    private PlotAPI plotAPI;

    @Override
    public void onEnable() {
        plotAPI = new PlotAPI();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onChangeBlock(EntityChangeBlockEvent entityChangeBlockEvent) {
        // Make sure the sand is stacking
        if (entityChangeBlockEvent.getEntityType() == EntityType.FALLING_BLOCK && entityChangeBlockEvent.getTo() != Material.AIR) {
            Entity entity = entityChangeBlockEvent.getEntity();

            // Is a sand entity
            if (!(entity instanceof FallingBlock)) {
                return;
            }

            // We're in a plot world
            Location source = ((FallingBlock) entity).getSourceLoc();
            if (!plotAPI.isPlotWorld(source.getWorld())) {
                return;
            }

            // Finally, if the plots aren't the same
            Plot sourcePlot = plotAPI.getPlot(source);
            Plot destinationPlot = plotAPI.getPlot(entity.getLocation());
            if (sourcePlot == destinationPlot) {
                return;
            }

            // Cancel sand stacking
            entityChangeBlockEvent.setCancelled(true);
        }
    }

}
