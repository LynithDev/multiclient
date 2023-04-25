package dev.lynith.Core.versions;

public interface IVersion {

    String getVersion();

    int getFps();

    String getPlayerName(); // This gets implemented in the version specific project

}
