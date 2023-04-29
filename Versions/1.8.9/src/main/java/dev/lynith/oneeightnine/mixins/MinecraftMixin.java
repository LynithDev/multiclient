package dev.lynith.oneeightnine.mixins;

import dev.lynith.Core.events.EventBus;
import dev.lynith.Core.events.impl.MinecraftInitEvent;
import dev.lynith.Core.events.impl.MinecraftShutdownEvent;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "startGame", at = @At("HEAD"))
    public void startGame(CallbackInfo ci) {
        EventBus.getEventBus().post(new MinecraftInitEvent());
    }

    @Inject(method = "getDebugFPS", at = @At("HEAD"))
    private static void getDebugFPS(CallbackInfoReturnable<Integer> cir) {
        System.out.println("MinecraftMixin.getDebugFPS");
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    public void shutdown(CallbackInfo ci) {
        EventBus.getEventBus().post(new MinecraftShutdownEvent());
    }

}
