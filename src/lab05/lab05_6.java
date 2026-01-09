import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class lab05_6 {
    public static void main(String[] args) {
        // สร้างหน้าต่างโปรแกรม
        JFrame frame = new JFrame("Save File with JMenuBar");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // สร้างพื้นที่สำหรับพิมพ์ข้อความ (TextArea)
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // สร้าง Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        
        // สร้างเมนูย่อย Save และ Exit
        JMenuItem menuSave = new JMenuItem("Save");
        JMenuItem menuExit = new JMenuItem("Exit");

        // เพิ่มเมนูย่อยเข้าเมนู File
        menuFile.add(menuSave);
        menuFile.addSeparator(); // เพิ่มเส้นคั่น
        menuFile.add(menuExit);

        // เพิ่มเมนู File เข้า Menu Bar และตั้งค่าให้ Frame
        menuBar.add(menuFile);
        frame.setJMenuBar(menuBar);

        // --- ฟังก์ชันการทำงานของเมนู Save ---
        menuSave.addActionListener(e -> {
            try {
                // ระบุตำแหน่งไฟล์ที่ต้องการบันทึก (ปรับเปลี่ยน Path ได้ตามต้องการ)
                FileWriter writer = new FileWriter("D:\\message.txt");
                writer.write(textArea.getText());
                writer.close();
                JOptionPane.showMessageDialog(frame, "Save successfully at D:\\message.txt");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error: Could not save file. " + ex.getMessage());
            }
        });

        // --- ฟังก์ชันการทำงานของเมนู Exit ---
        menuExit.addActionListener(e -> {
            System.exit(0);
        });

        // แสดงหน้าต่างโปรแกรม
        frame.setVisible(true);
    }
}
