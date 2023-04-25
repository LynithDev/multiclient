package dev.lynith.start;

import dev.lynith.Core.versions.IVersion;
import dev.lynith.oneeightnine.Version;

public class VersionMain {

    public static Class<? extends IVersion> getVersion() {
        return Version.class;
    }

}
