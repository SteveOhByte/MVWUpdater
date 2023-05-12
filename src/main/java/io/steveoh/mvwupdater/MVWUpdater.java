package io.steveoh.mvwupdater;

import com.formdev.flatlaf.FlatDarkLaf;
import com.moandjiezana.toml.Toml;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MVWUpdater {

    public MVWUpdater() {
        // Set the look and feel to FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.out.println("Unsupported look and feel");
        }

        // If the program cannot connect to the internet, return
        if (!isNetworkConnected()) {
            return;
        }

        // Path setup
        String jarFilePath = new File(System.getProperty("user.dir")).getPath();
        String configFilePath = jarFilePath + File.separator + "config.toml";

        // Check if the config file exists, if not, return
        File configFile = new File(configFilePath);
        if (!configFile.exists()) {
            System.out.println("Config file does not exist");
            return;
        }

        String currentVersion = null;
        String latestVersionDownloadURL = null;
        String changelogDownloadURL = null;
        String changelog = null;
        String latestPackageDownloadURL = null;
        String latestVersion = null;
        String programFilesPath = null;
        String programName = null;
        String configText = null;
        boolean update = false;

        // Config handling
        try {
            configText = new String(Files.readAllBytes(Paths.get(configFilePath)));
            Toml config = new Toml().read(configText);

            currentVersion = config.getString("currentVersion");
            latestVersionDownloadURL = config.getString("latestVersionDownloadURL");
            changelogDownloadURL = config.getString("changeLogDownloadURL");
            latestPackageDownloadURL = config.getString("latestPackageDownloadURL");
            programFilesPath = config.getString("programFilesPath");
            programName = config.getString("programName");

            // Making sure the config is valid
            if (currentVersion == null || currentVersion.isEmpty()) {
                System.out.println("Current version is not set in config.toml");
                return;
            }
            // Verify that the version string is a format of X.X.X
            if (!currentVersion.matches("\\d+\\.\\d+\\.\\d+")) {
                System.out.println("Current version is not in the format of X.X.X");
                return;
            }

            if (latestVersionDownloadURL == null || latestVersionDownloadURL.isEmpty()) {
                System.out.println("Latest version download URL is not set in config.toml");
                return;
            }
            if (changelogDownloadURL == null || changelogDownloadURL.isEmpty()) {
                System.out.println("Changelog download URL is not set in config.toml");
                return;
            }
            if (latestPackageDownloadURL == null || latestPackageDownloadURL.isEmpty()) {
                System.out.println("Latest package download URL is not set in config.toml");
                return;
            }
            if (programFilesPath == null || programFilesPath.isEmpty()) {
                System.out.println("Program files path is not set in config.toml");
                return;
            }

            // Verify that the program files path exists
            File programFilesDirectory = new File(programFilesPath);
            if (!programFilesDirectory.exists()) {
                System.out.println("Program files path does not exist");
                return;
            }

            if (programName == null || programName.isEmpty()) {
                System.out.println("Program name is not set in config.toml");
                return;
            }

            // Download the latest version
            latestVersion = downloadText(latestVersionDownloadURL).trim();
            if (latestVersion == null || latestVersion.isEmpty()) {
                System.out.println("Failed to download latest version");
                return;
            }

            // Check if the latest version is newer than the current version
            if (!isVersionGreater(latestVersion, currentVersion)) {
                System.out.println("No new version available");
                return;
            }
            
            // Download the changelog
            changelog = downloadText(changelogDownloadURL).trim();
            if (changelog == null || changelog.isEmpty()) {
                System.out.println("Failed to download changelog");
                return;
            }

            update = true;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (!update) {
            return;
        }

        new MainForm(programName, currentVersion, latestVersion, changelog, jarFilePath, latestPackageDownloadURL, programFilesPath, configFilePath).setVisible(true);
    }

    private String downloadText(String url) {
        URL downloadUrl = null;
        try {
            // Connect to the url and download the text to the reader
            downloadUrl = new URL(url);
            URLConnection connection = downloadUrl.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // Read the text from the reader and return it
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }

            reader.close();
            return content.toString();
        } catch (IOException e) {
            // Check if the url is real and reachable
            try {
                downloadUrl = new URL(url);
                URLConnection connection = downloadUrl.openConnection();
                connection.connect();
            } catch (IOException e1) {
                System.out.println("Failed to connect to " + url);
                System.exit(1);
            }

            throw new RuntimeException(e);
        }
    }

    // This function is used to determine if the latest version is greater than the current version
    private boolean isVersionGreater(String version1, String version2) {
        String[] version1Parts = version1.split("\\.");
        String[] version2Parts = version2.split("\\.");

        for (int i = 0; i < version1Parts.length; i++) {
            int version1Part = Integer.parseInt(version1Parts[i]);
            int version2Part = Integer.parseInt(version2Parts[i]);

            if (version1Part > version2Part) {
                return true;
            } else if (version1Part < version2Part) {
                return false;
            }
        }

        return false;
    }

    // This function is used to determine if the user is connected to the internet
    // It does this by trying to connect to a few different domains that are known to be up
    private boolean isNetworkConnected() {
        String[] hostnames = { "www.google.com", "www.facebook.com", "www.apple.com", "www.microsoft.com" };
        
        for (String hostname : hostnames) {
            try {
                InetAddress address = InetAddress.getByName(hostname);
                if (address.isReachable(90 * 1000)) { // timeout after 90 seconds
                    return true;
                }
            } catch (Exception e) {
                // ignore and try next hostname
                System.out.println(e.getMessage());
            }
        }

        System.out.println("No network connection");
        return false;
    }
    
    public static void main(String[] args) { new MVWUpdater(); }
}
