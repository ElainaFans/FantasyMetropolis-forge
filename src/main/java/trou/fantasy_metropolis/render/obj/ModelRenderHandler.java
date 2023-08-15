package trou.fantasy_metropolis.render.obj;

import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import trou.fantasy_metropolis.FantasyMetropolis;
import trou.fantasy_metropolis.Registries;

@Mod.EventBusSubscriber(modid = FantasyMetropolis.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModelRenderHandler {
    @SubscribeEvent
    public void onBakingResult(ModelEvent.ModifyBakingResult event) {
        if (Registries.ITEM_SWORD_WHITER.getKey() != null) {
            var location = ModelLocationUtils.getModelLocation(Registries.ITEM_SWORD_WHITER.get());
            var existingModel = event.getModels().get(location);
            event.getModels().put(location, new WhiterSwordModel(existingModel, existingModel));
        }
    }
}
