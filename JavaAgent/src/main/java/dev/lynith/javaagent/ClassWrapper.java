package dev.lynith.javaagent;

import dev.lynith.javaagent.mixin.ClientMixinGlobalPropertyService;
import dev.lynith.javaagent.mixin.ClientMixinService;
import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassWrapper extends URLClassLoader {

    @Getter
    private static ClassWrapper instance;

    private final ClassLoader upstream;

    ClassWrapper(URL[] urls) {
        super(urls);
        this.upstream = getSystemClassLoader();
        instance = this;
        Thread.currentThread().setContextClassLoader(this);
    }

    private static final String[] EXCLUDED_PACKAGES = {
            "java",
            "javax",
            "sun",
            "com.sun",
            "jdk",
            "org.apache",
            "io.netty",
            "com.mojang.authlib",
            "com.mojang.util",
            "com.google",
            "net.hypixel",
            "org.spongepowered",
    };
    private static final String[] EXCLUDED_CLASSES = {
            "org.lwjgl.Version"
    };

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> preexisting = findLoadedClass(name);
            if (preexisting != null)
                return preexisting;

            if (!name.startsWith("org.spongepowered.asm.synthetic.") && !name.startsWith("javax.vecmath.")) {
                for (String exclude : EXCLUDED_CLASSES)
                    if (name.equals(exclude))
                        return upstream.loadClass(name);

                for (String exclude : EXCLUDED_PACKAGES)
                    if (name.startsWith(exclude) && name.charAt(exclude.length()) == '.')
                        return upstream.loadClass(name);
            }

            Class<?> found = findClass(name);
            if (found == null)
                return upstream.loadClass(name);

            return found;
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = getTransformedBytes(name);
        if (data == null)
            return null;

        return defineClass(name, data, 0, data.length);
    }

    public byte[] getTransformedBytes(String name) throws ClassNotFoundException {
        return getTransformedBytes(name, true);
    }

    public byte[] getTransformedBytes(String name, boolean mixin) throws ClassNotFoundException {
        try {
            URL resource = getResource(getClassFileName(name));
            if (resource == null)
                return ClientMixinService.getTransformer().generateClass(MixinEnvironment.getDefaultEnvironment(), name);

            byte[] data;
            try (InputStream in = resource.openStream()) {
                data = IOUtils.toByteArray(in);
            }

            if (mixin)
                data = ClientMixinService.getTransformer().transformClass(MixinEnvironment.getDefaultEnvironment(), name,
                        data);

            return data;
        } catch (Throwable error) {
            throw new ClassNotFoundException(name, error);
        }
    }

    private static String getClassFileName(String name) {
        return name.replace('.', '/') + ".class";
    }

    static {
        registerAsParallelCapable();
    }
}