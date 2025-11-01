import javax.swing.*;
import java.awt.*;
import java.io.*;

public class todo {
    public static void main(String[] args) {

        // setting up the frame

        JFrame frame = new JFrame("Your TO-DO List✨🌷");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(null);

        frame.getContentPane().setBackground(new Color(255, 228, 232));

        JButton button = new JButton("Add Tasks💪🏻");
        button.setBounds(180, 20, 100, 25);
        button.setBackground(new Color(200, 170, 255)); // lavender
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        JTextField textField = new JTextField();
        textField.setBounds(20, 20, 150, 25);
        textField.setBackground(new Color(255, 240, 245));
        textField.setForeground(new Color(90, 60, 70));
        textField.setCaretColor(new Color(160, 110, 120));
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createLineBorder(new Color(255, 200, 210), 2));

        JPanel taskpanel = new JPanel();
        taskpanel.setLayout(new BoxLayout(taskpanel, BoxLayout.Y_AXIS));

        loadTasks(taskpanel);
        // scroll pane for list
        JScrollPane scrollPane = new JScrollPane(taskpanel);
        scrollPane.setBounds(20, 60, 340, 280);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        button.addActionListener(e -> {
            String task = textField.getText().trim();
            if (!task.isEmpty()) {
                JCheckBox taskCheckBox = new JCheckBox(task);
                taskCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                taskpanel.add(taskCheckBox);
                taskpanel.revalidate();
                taskpanel.repaint();
                textField.setText("");
                saveTasks(taskpanel);
            } else {
                JOptionPane.showMessageDialog(frame, "please enter a damn task!");
            }

        });

        frame.add(button);
        frame.add(textField);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private static void saveTasks(JPanel taskJPanel) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("task.txt"))) {
            for (Component comp : taskJPanel.getComponents()) {
                if (comp instanceof JCheckBox) {
                    JCheckBox box = (JCheckBox) comp;
                    writer.write(box.getText() + "," + box.isSelected());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    private static void loadTasks(JPanel taskPanel) {
        File file = new File("task.txt");
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String text = parts[0];
                boolean done = parts.length > 1 && Boolean.parseBoolean(parts[1]);

                JCheckBox taskCheckBox = new JCheckBox(text);
                taskCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                taskCheckBox.setSelected(done);
                taskPanel.add(taskCheckBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
