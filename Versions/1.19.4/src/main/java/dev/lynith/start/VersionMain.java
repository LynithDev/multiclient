package dev.lynith.start;

import dev.lynith.Core.versions.IVersion;
import dev.lynith.Core.versions.IVersionMain;
import dev.lynith.onenineteenfour.Version;

public class VersionMain implements IVersionMain {

    @Override
    public Class<? extends IVersion> getVersion() {
        return Version.class;
    }

}
