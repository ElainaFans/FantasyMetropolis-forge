package trou.fantasy_metropolis.mixin;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import trou.fantasy_metropolis.render.combat.WhiterCombatRenderer;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {
    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Z)V")
    private void init(EntityRendererProvider.Context pContext, boolean pUseSlimModel, CallbackInfo ci) {
        PlayerRenderer me = ((PlayerRenderer) (Object) this);
        me.addLayer(new WhiterCombatRenderer(me));
    }
}
