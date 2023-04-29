package dev.lynith.oneeightnine.mixins;

import dev.lynith.Core.events.EventBus;
import dev.lynith.Core.events.impl.MinecraftInitEvent;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "startGame", at = @At("RETURN"))
    public void startGame(CallbackInfo ci) {
        System.out.println("MinecraftMixin.startGame");
        EventBus.getEventBus().post(new MinecraftInitEvent());
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    public void shutdown(CallbackInfo ci) {
        System.out.println("MinecraftMixin.shutdown");
    }

}
