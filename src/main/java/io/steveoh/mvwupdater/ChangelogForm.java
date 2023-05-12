package io.steveoh.mvwupdater;

import javax.swing.*;

// Simple form for displaying the changelog
public class ChangelogForm extends javax.swing.JFrame {

    // Form variables
    private JScrollPane scrollPane;
    private JTextArea textArea;

    // Program variables
    private String changelog;
    
    public ChangelogForm(String changelog) {
        this.changelog = changelog;
        initComponents();
        
        String text = "";

        // Add a bullet point for each line in the changelog
        String[] lines = changelog.split("\n");
        for (String line : lines) {
            text += "    ● " + line + "\n";
        }
        textArea.setText(text);
    }

    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();

        // Dispose instead of exit on close so the program doesn't close when the changelog is closed
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Changelog");
        setResizable(false);

        textArea.setEditable(false);
        textArea.setColumns(20);
        textArea.setFont(new java.awt.Font("Segoe UI", 0, 18));
        textArea.setRows(5);
        scrollPane.setViewportView(textArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }
}
