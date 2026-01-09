import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

public class lab05_4 {

    public static void main(String[] args) {

        // สร้าง Frame
        JFrame frame = new JFrame("Program with JTextArea");
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // สร้าง TextArea
        JTextArea textArea = new JTextArea(8, 30);
        textArea.setLineWrap(true);       // ตัดบรรทัดอัตโนมัติ
        textArea.setWrapStyleWord(true);  // ตัดตามคำ

        // ใส่ ScrollBar ให้ JTextArea
        JScrollPane scrollPane = new JScrollPane(textArea);

        // สร้างปุ่ม
        JButton btnShow = new JButton("Show message");
        JButton btnSave = new JButton("Save");

        // เมื่อกดปุ่ม Show message
        btnShow.addActionListener(e -> {
            String text = textArea.getText();
            JOptionPane.showMessageDialog(frame, text, 
                "Your message: ", JOptionPane.INFORMATION_MESSAGE);
        });

        // เมื่อกดปุ่ม Save ให้บันทึกไฟล์ลง Drive D:
        btnSave.addActionListener(e -> {
            try {
                FileWriter writer = new FileWriter("D:\\message.txt");
                writer.write(textArea.getText());
                writer.close();
                JOptionPane.showMessageDialog(frame, "บันทึกข้อมูลสำเร็จแล้วที่ D:\\message.txt");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "เกิดข้อผิดพลาด: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // เพิ่ม Component ลงใน Frame
        frame.add(scrollPane);
        frame.add(btnShow);
        frame.add(btnSave);

        frame.setVisible(true);
    }
}
