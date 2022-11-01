package com.cheatbreaker.client.remote;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GitCommitProperties {
    public static void loadProperties() {
        try {
            final ResourceLocation resourceLocation = new ResourceLocation("client/properties/app.properties");
            final Properties properties = new Properties();
            InputStream inputStream = Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation).getInputStream();

            if (inputStream == null) {
                gitCommit = "?";
                gitCommitId = "?";
                gitBranch = "?";
                return;
            }

            properties.load(inputStream);
            gitCommit = properties.getProperty("git.commit.id.abbrev");
            gitCommitId = properties.getProperty("git.commit.id");
            gitBranch = properties.getProperty("git.branch");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Getter
    private static String gitCommit = "?";
    @Getter
    private static String gitCommitId = "?";
    @Getter
    private static String gitBranch = "?";
}
