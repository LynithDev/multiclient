package dev.lynith.javaagent.mixin;

import org.spongepowered.asm.mixin.MixinEnvironment;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ClientMixinTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        return new ClientMixinService().getTransformer().transformClass(MixinEnvironment.getDefaultEnvironment(), className.replace("/", "."), classfileBuffer);
    }
}
