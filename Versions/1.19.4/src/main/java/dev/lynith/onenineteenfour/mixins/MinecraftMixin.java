package dev.lynith.onenineteenfour.mixins;

import dev.lynith.Core.events.EventBus;
import dev.lynith.Core.events.impl.MinecraftInitEvent;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "run", at = @At("RETURN"))
    public void run(CallbackInfo info) {
        EventBus.getEventBus().post(new MinecraftInitEvent());
    }

}
