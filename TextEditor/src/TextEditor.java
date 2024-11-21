import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextEditor extends JFrame implements ActionListener {
    JTextArea textArea;
    JScrollPane scrollPane;
    JSpinner fontSizeSpinner;
    JButton fontColorButton;
    JComboBox<String> fontBox;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem saveItem;

    TextEditor() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Bruno's Text Editor");
        this.setSize(500, 500);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));
        textArea.setText("Ovdje možeš pisati svoj tekst...");

        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Spinner za veličinu fonta
        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
        fontSizeSpinner.setValue(20);

        fontSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue()));
            }
        });

        // Dugme za izbor boje
        fontColorButton = new JButton("Color");
        fontColorButton.addActionListener(this);

        // Dropdown za izbor fonta
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontBox = new JComboBox<>(fonts);
        fontBox.setSelectedItem("Arial");
        fontBox.addActionListener(this);

        // Panel za opcije
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Font Size:"));
        topPanel.add(fontSizeSpinner);
        topPanel.add(fontColorButton);
        topPanel.add(new JLabel("Font:"));
        topPanel.add(fontBox);

        // Dodavanje elemenata u prozor
        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        // Kreiranje menija
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        saveItem = new JMenuItem("Save");
        saveItem.addActionListener(this);

        fileMenu.add(saveItem);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fontColorButton) {
            Color color = JColorChooser.showDialog(null, "Choose a color", Color.BLACK);
            if (color != null) {
                textArea.setForeground(color);
            }
        } else if (e.getSource() == fontBox) {
            String selectedFont = (String) fontBox.getSelectedItem();
            int fontSize = (int) fontSizeSpinner.getValue();
            textArea.setFont(new Font(selectedFont, Font.PLAIN, fontSize));
        } else if (e.getSource() == saveItem) {
            // Implementacija funkcionalnosti za spremanje teksta u fajl
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(this);

            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(textArea.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new TextEditor();
    }
}
