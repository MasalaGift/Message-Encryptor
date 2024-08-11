package za.ac.tut.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import za.ac.tut.encryption.MessageEncryptor;

public class SecureMessagesFrame extends JFrame {
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel main;
    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem openFile;
    private JMenuItem encryptMessage;
    private JMenuItem saveEncryptedMessage;
    private JMenuItem clear;
    private JMenuItem exit;
    private JLabel titleLabel;
    private JScrollPane scroll;
    private JScrollPane scroll_1;
    private JTextArea plainTxtArea;
    private JTextArea secureTxtArea;

    public SecureMessagesFrame() {
        setTitle("Secure Messages");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600); 
        setLocationRelativeTo(null);
        
        // Initialize panels
        panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        main = new JPanel(new BorderLayout(10, 5));
        
        // Initialize menu components
        menuBar = new JMenuBar();
        file = new JMenu("File");
        openFile = new JMenuItem("Open file...");
        encryptMessage = new JMenuItem("Encrypt message...");
        saveEncryptedMessage = new JMenuItem("Save encrypted message...");
        clear = new JMenuItem("Clear");
        exit = new JMenuItem("Exit");
        
        // Add menu items to the menu and menu bar
        file.add(openFile);
        file.add(encryptMessage);
        file.add(saveEncryptedMessage);
        file.add(clear);
        file.add(exit);
        menuBar.add(file);
        
        // Set menu bar
        setJMenuBar(menuBar);
        
        // Set up title label
        titleLabel = new JLabel("Message Encryptor");
        titleLabel.setFont(new Font(Font.SERIF, Font.BOLD + Font.ITALIC, 40));
        titleLabel.setForeground(Color.blue);
        titleLabel.setBorder(new LineBorder(Color.BLACK,1));
        
        // Set up text areas and scroll panes
        plainTxtArea = new JTextArea(20, 30);
        secureTxtArea = new JTextArea(20, 30);
        scroll = new JScrollPane(plainTxtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBorder(new TitledBorder(new LineBorder(Color.BLACK,1),"Plain message"));
        scroll_1 = new JScrollPane(secureTxtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll_1.setBorder(new TitledBorder(new LineBorder(Color.BLACK,1),"Encrypted message"));
        
        // Add components to panels
        panel1.add(titleLabel);
        panel2.add(scroll);
        panel3.add(scroll_1);
        
        // Add panels to the main panel with correct layout
        main.add(panel1, BorderLayout.NORTH);
        main.add(panel2, BorderLayout.WEST);
        main.add(panel3, BorderLayout.EAST);
    
        // Add the main panel to the frame
        add(main);

        openFile.addActionListener(new OpenFileListerner());
        encryptMessage.addActionListener(new EncrypteMessageListener());
        saveEncryptedMessage.addActionListener(new SaveEncryptedMessageListener());
        clear.addActionListener(new ClearListener());
        exit.addActionListener(new ExitListener());
        
        setVisible(true);
    }

    // Method to read the content of a file
    private String readFile(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    // Method to write content to a file
    private void writeFile(File file, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        }
    }
    
    public class OpenFileListerner implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    String content = readFile(file);
                    plainTxtArea.setText(content);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                }
            }
        }
    
    }
    
    public class EncrypteMessageListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String plainText = plainTxtArea.getText();
            MessageEncryptor encryptor = new MessageEncryptor();
            String encryptedText = encryptor.encrypt(plainText);
            secureTxtArea.setText(encryptedText);
        }
    }
    
    public class SaveEncryptedMessageListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    String content = secureTxtArea.getText();
                    writeFile(file, content);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                }
            }
        }
    }
    
    public class ClearListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            plainTxtArea.setText("");
            secureTxtArea.setText("");
        }
    }
    
    public class ExitListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
