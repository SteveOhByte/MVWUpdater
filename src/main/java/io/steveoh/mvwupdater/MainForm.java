package io.steveoh.mvwupdater;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MainForm extends javax.swing.JFrame {

    // Form variables
    private JButton changelogButton;
    private JLabel currentVersionLabel;
    private JLabel firstPromptLabel;
    private JLabel latestVersionLabel;
    private JProgressBar progressBar;
    private JLabel secondPromptLabel;
    private JButton skipButton;
    private JButton updateButton;

    // Program variables
    private String programName;
    private String currentVersion;
    private String latestVersion;
    private String changelog;
    private String jarFilePath;
    private String latestPackageDownloadURL;
    private String programFilesPath;
    private String configFilePath;
    
    public MainForm(String programName, String currentVersion, String latestVersion, String changelog, String jarFilePath, String latestPackageDownloadURL, String programFilesPath, String configFilePath) {
        this.programName = programName;
        this.currentVersion = currentVersion;
        this.latestVersion = latestVersion;
        this.changelog = changelog;
        this.jarFilePath = jarFilePath;
        this.latestPackageDownloadURL = latestPackageDownloadURL;
        this.programFilesPath = programFilesPath;
        this.configFilePath = configFilePath;

        // Initialize the form
        initComponents();

        ImageIcon icon = null;
        try {
            icon = new ImageIcon(ImageIO.read(new File(System.getProperty("user.dir") + File.separator + "icon.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setIconImage(icon.getImage());

        progressBar.setVisible(false);
        progressBar.setStringPainted(true);
        // Set all the relevant text
        this.setTitle(programName + " Update");
        firstPromptLabel.setText("A new version of " + programName + " is available.");
        currentVersionLabel.setText("Current Version: " + currentVersion);
        latestVersionLabel.setText("Latest Version: " + latestVersion);
    }

    private void initComponents() {

        firstPromptLabel = new javax.swing.JLabel();
        currentVersionLabel = new javax.swing.JLabel();
        latestVersionLabel = new javax.swing.JLabel();
        secondPromptLabel = new javax.swing.JLabel();
        changelogButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        skipButton = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update");
        setName("mainFrame");
        setResizable(false);


        firstPromptLabel.setFont(new java.awt.Font("Segoe UI", 0, 16));
        firstPromptLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        firstPromptLabel.setText("A new version of app_name is available.");

        currentVersionLabel.setFont(new java.awt.Font("Segoe UI", 0, 16));
        currentVersionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currentVersionLabel.setText("Current Version: X.X.X");

        latestVersionLabel.setFont(new java.awt.Font("Segoe UI", 0, 16));
        latestVersionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        latestVersionLabel.setText("Latest Version: X.X.X");

        secondPromptLabel.setFont(new java.awt.Font("Segoe UI", 0, 16));
        secondPromptLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        secondPromptLabel.setText("Would you like to update?");

        changelogButton.setFont(new java.awt.Font("Segoe UI", 0, 14));
        changelogButton.setText("Changelog");
        changelogButton.addActionListener(evt -> changelogButtonActionPerformed(evt));

        updateButton.setFont(new java.awt.Font("Segoe UI", 0, 18));
        updateButton.setText("Update");
        updateButton.addActionListener(evt -> updateButtonActionPerformed(evt));

        skipButton.setFont(new java.awt.Font("Segoe UI", 0, 18));
        skipButton.setText("Skip");
        skipButton.addActionListener(evt -> skipButtonActionPerformed(evt));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(firstPromptLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(latestVersionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(currentVersionLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(secondPromptLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(161, Short.MAX_VALUE)
                .addComponent(changelogButton)
                .addGap(161, 161, 161))
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(updateButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(skipButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(63, 63, 63))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(firstPromptLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(currentVersionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(latestVersionLabel)
                .addGap(18, 18, 18)
                .addComponent(changelogButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(secondPromptLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateButton)
                    .addComponent(skipButton))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void changelogButtonActionPerformed(java.awt.event.ActionEvent evt) {
        new ChangelogForm(changelog).setVisible(true);
    }

    private void skipButtonActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // Hide and resize elements for download progression
        skipButton.setEnabled(false);
        updateButton.setEnabled(false);
        firstPromptLabel.setVisible(false);
        secondPromptLabel.setVisible(false);
        progressBar.setVisible(true);
        currentVersionLabel.setText("Downloading...");
        latestVersionLabel.setVisible(false);
        changelogButton.setVisible(false);
        this.setSize(438, 200);
        update();
    }

    // This function downloads the latest version of the program and extracts it to a temporary directory
    private void downloadAndExtract7zFile(String url, String destinationDirectory) {
        try {
            // Open a connection to the download URL
            URL downloadUrl = new URL(url);
            URLConnection connection = downloadUrl.openConnection();

            // Get the size of the file
            int fileSize = connection.getContentLength();

            // Create a temporary file and initialize the input and output streams
            File tempFile = File.createTempFile(UUID.randomUUID().toString(), ".7z", new File(destinationDirectory).getParentFile());
            BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());
            FileOutputStream outputStream = new FileOutputStream(tempFile);

            // Download the file
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            int totalBytesRead = 0;
            while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                int progress = (int) ((totalBytesRead / (double)fileSize) * 100);
                progressBar.setValue(progress); // Update the progress bar
            }

            // Close the streams
            outputStream.close();
            inputStream.close();

            // Prepare to extract the file
            SevenZFile sevenZFile = new SevenZFile(tempFile);
            SevenZArchiveEntry entry;
            progressBar.setValue(0);
            currentVersionLabel.setText("Extracting...");

            // Extract the file
            while ((entry = sevenZFile.getNextEntry()) != null) {
                if (!entry.isDirectory() && !entry.getName().equals("kDummy")) {
                    String entryName = entry.getName();
                    File entryFile = new File(destinationDirectory + File.separator + entryName);
                    File entryDir = entryFile.getParentFile();
                    if (!entryDir.exists()) {
                        entryDir.mkdirs();
                    }

                    BufferedOutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(entryFile));
                    byte[] buffer2 = new byte[1024];
                    int bytesRead2 = 0;
                    int totalBytesRead2 = 0;
                    while ((bytesRead2 = sevenZFile.read(buffer2, 0, buffer2.length)) != -1) {
                        outputStream2.write(buffer2, 0, bytesRead2);
                        totalBytesRead2 += bytesRead2;
                        int progress = (int) ((totalBytesRead2 / (double) entry.getSize()) * 100);
                        progressBar.setValue(progress); // Update the progress bar
                    }
                    outputStream2.close();
                }
                progressBar.setValue(0);
            }

            currentVersionLabel.setText("Installing...");

            sevenZFile.close();
            tempFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void update() {
        // Create a SwingWorker to perform the update process in the background
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Create a temporary directory
                File tempDir = new File(jarFilePath + File.separator + "temp");
                if (!tempDir.exists()) {
                    tempDir.mkdir();
                }

                // Download the latest package
                downloadAndExtract7zFile(latestPackageDownloadURL, tempDir.getAbsolutePath());

                // Move all files and folders recursively to the program files directory
                File tempDirContents[] = tempDir.listFiles();
                double totalFiles = tempDirContents.length;
                double filesMoved = 0;
                for (File file : tempDirContents) {
                    File destinationFile = new File(programFilesPath + File.separator + file.getName());
                    // If the file already exists, delete it
                    if (destinationFile.exists()) {
                        if (destinationFile.isDirectory()) {
                            try {
                                FileUtils.deleteDirectory(destinationFile);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            destinationFile.delete();
                        }
                    }
                    if (file.isDirectory()) {
                        try {
                            FileUtils.moveDirectory(file, destinationFile);
                            filesMoved += 1;
                            int progress = (int) ((filesMoved / totalFiles) * 100);
                            publish(progress); // Publish progress update to the GUI thread
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            FileUtils.moveFile(file, destinationFile);
                            filesMoved += 1;
                            int progress = (int) ((filesMoved / totalFiles) * 100);
                            publish(progress); // Publish progress update to the GUI thread
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                // Delete the temporary directory
                try {
                    Files.delete(tempDir.toPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                currentVersionLabel.setText("Update Installed!");

                try {
                    File configFile = new File(configFilePath);
                    BufferedReader reader = new BufferedReader(new FileReader(configFile));
                    String line = reader.readLine();
                    StringBuilder newConfig = new StringBuilder();
                    while (line != null) {
                        if (line.contains("currentVersion")) {
                            line = "currentVersion = \"" + latestVersion + "\"";
                        }
                        newConfig.append(line).append("\n");
                        line = reader.readLine();
                    }
                    reader.close();
                    BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
                    writer.write(newConfig.toString());
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                // Update the progress bar with the latest progress value
                progressBar.setValue(chunks.get(chunks.size() - 1));
            }

            @Override
            protected void done() {
            }
        };

        // Start the SwingWorker to perform the update process in the background
        worker.execute();
    }
}
