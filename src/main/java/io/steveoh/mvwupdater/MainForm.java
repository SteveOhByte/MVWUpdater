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

    private void update() {
        // Create a SwingWorker to perform the update process in the background
        SwingWorker<Void, UpdateProgress> worker = new SwingWorker<Void, UpdateProgress>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    // Create a temporary directory
                    File tempDir = new File(jarFilePath + File.separator + "temp");
                    if (!tempDir.exists()) {
                        tempDir.mkdir();
                    }

                    // DOWNLOAD PHASE
                    publish(new UpdateProgress("Downloading...", 0));

                    // Open a connection to the download URL
                    URL downloadUrl = new URL(latestPackageDownloadURL);
                    URLConnection connection = downloadUrl.openConnection();

                    // Get the size of the file
                    int fileSize = connection.getContentLength();
                    if (fileSize <= 0) {
                        throw new Exception("Could not determine file size");
                    }

                    // Create a temporary file and initialize the input and output streams
                    File tempFile = File.createTempFile(UUID.randomUUID().toString(), ".7z", tempDir.getParentFile());
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
                        publish(new UpdateProgress("Downloading...", progress));
                    }

                    // Close the streams
                    outputStream.close();
                    inputStream.close();

                    // EXTRACTION PHASE
                    publish(new UpdateProgress("Extracting...", 0));

                    // Prepare to extract the file
                    SevenZFile sevenZFile = new SevenZFile(tempFile);
                    SevenZArchiveEntry entry;

                    // Get total entries for progress calculation
                    long totalEntries = 0;
                    while ((entry = sevenZFile.getNextEntry()) != null) {
                        if (!entry.isDirectory() && !entry.getName().equals("kDummy")) {
                            totalEntries++;
                        }
                    }
                    sevenZFile.close();

                    // Reopen for actual extraction
                    sevenZFile = new SevenZFile(tempFile);
                    long processedEntries = 0;

                    // Extract the file
                    while ((entry = sevenZFile.getNextEntry()) != null) {
                        if (!entry.isDirectory() && !entry.getName().equals("kDummy")) {
                            String entryName = entry.getName();
                            File entryFile = new File(tempDir.getAbsolutePath() + File.separator + entryName);
                            File entryDir = entryFile.getParentFile();
                            if (!entryDir.exists()) {
                                entryDir.mkdirs();
                            }

                            BufferedOutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(entryFile));
                            byte[] buffer2 = new byte[1024];
                            int bytesRead2 = 0;

                            while ((bytesRead2 = sevenZFile.read(buffer2, 0, buffer2.length)) != -1) {
                                outputStream2.write(buffer2, 0, bytesRead2);
                            }
                            outputStream2.close();

                            processedEntries++;
                            int progress = (int) ((processedEntries / (double) totalEntries) * 100);
                            publish(new UpdateProgress("Extracting...", progress));
                        }
                    }

                    sevenZFile.close();
                    tempFile.delete();

                    // INSTALLATION PHASE
                    publish(new UpdateProgress("Installing...", 0));

                    // Move all files and folders recursively to the program files directory
                    File tempDirContents[] = tempDir.listFiles();
                    if (tempDirContents == null || tempDirContents.length == 0) {
                        throw new Exception("Extraction failed - no files found");
                    }

                    double totalFiles = tempDirContents.length;
                    double filesMoved = 0;

                    for (File file : tempDirContents) {
                        File destinationFile = new File(programFilesPath + File.separator + file.getName());
                        // If the file already exists, delete it
                        if (destinationFile.exists()) {
                            if (destinationFile.isDirectory()) {
                                FileUtils.deleteDirectory(destinationFile);
                            } else {
                                destinationFile.delete();
                            }
                        }

                        if (file.isDirectory()) {
                            FileUtils.moveDirectory(file, destinationFile);
                        } else {
                            FileUtils.moveFile(file, destinationFile);
                        }

                        filesMoved += 1;
                        int progress = (int) ((filesMoved / totalFiles) * 100);
                        publish(new UpdateProgress("Installing...", progress));
                    }

                    // Delete the temporary directory
                    Files.delete(tempDir.toPath());

                    // Update the config file
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

                    publish(new UpdateProgress("Update Installed!", 100));

                } catch (Exception e) {
                    // Log the actual error
                    e.printStackTrace();
                    publish(new UpdateProgress("Update Failed: " + e.getMessage(), 0));
                    throw e; // Re-throw to trigger done() method error handling
                }

                return null;
            }

            @Override
            protected void process(java.util.List<UpdateProgress> chunks) {
                // Update the progress bar and label with the latest values
                UpdateProgress latest = chunks.get(chunks.size() - 1);
                currentVersionLabel.setText(latest.message);
                progressBar.setValue(latest.progress);
            }

            @Override
            protected void done() {
                try {
                    get(); // This will throw any exception that occurred in doInBackground()
                } catch (Exception e) {
                    currentVersionLabel.setText("Update Failed!");
                    progressBar.setValue(0);
                    e.printStackTrace();
                    // Optionally show an error dialog
                    JOptionPane.showMessageDialog(MainForm.this,
                            "Update failed: " + e.getMessage(),
                            "Update Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        // Start the SwingWorker to perform the update process in the background
        worker.execute();
    }
}

class UpdateProgress {
    public final String message;
    public final int progress;

    public UpdateProgress(String message, int progress) {
        this.message = message;
        this.progress = progress;
    }
}