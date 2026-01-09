package lab05;

	import javax.swing.*;
	import java.awt.*;
	import java.awt.event.*;
	import java.io.*;

	public class lab05_8 extends JFrame {

	    private JTextArea textArea;
	    private File currentFile = null; // ตัวแปรเก็บไฟล์ปัจจุบันที่เปิดอยู่

	    public lab05_8() {
	        // --- 1. ตั้งค่า Title (เปลี่ยนเป็น ชื่อ-นามสกุล รหัสนิสิต ของคุณ) ---
	        super("Wutthiphat Angsri N20 - Text Editor");

	        setSize(800, 600);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLayout(new BorderLayout());

	        // --- 2. สร้างพื้นที่พิมพ์ข้อความ (TextArea) ---
	        textArea = new JTextArea();
	        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14)); // ตั้งฟอนต์ให้อ่านง่าย
	        textArea.setEditable(true); // ให้พิมพ์แก้ไขได้

	        JScrollPane scrollPane = new JScrollPane(textArea);
	        add(scrollPane, BorderLayout.CENTER);

	        // --- 3. สร้างเมนูบาร์ ---
	        JMenuBar menuBar = new JMenuBar();
	        JMenu fileMenu = new JMenu("File");

	        // สร้างรายการเมนูย่อย
	        JMenuItem itemNew = new JMenuItem("New");
	        JMenuItem itemOpen = new JMenuItem("Open");
	        JMenuItem itemSave = new JMenuItem("Save");
	        JMenuItem itemSaveAs = new JMenuItem("Save As");
	        JMenuItem itemExit = new JMenuItem("Exit");

	        // --- 4. ใส่ Action ให้แต่ละเมนู ---

	        // NEW: ล้างหน้าจอ
	        itemNew.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                textArea.setText("");
	                currentFile = null;
	                setTitle("New File - My Text Editor");
	            }
	        });

	        // OPEN: เปิดไฟล์
	        itemOpen.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                openFile();
	            }
	        });

	        // SAVE: บันทึกทับไฟล์เดิม (ถ้ามี) หรือ Save As (ถ้าไม่มี)
	        itemSave.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                if (currentFile != null) {
	                    saveFile(currentFile); // บันทึกทับไฟล์เดิม
	                } else {
	                    saveAsFile(); // ถ้ายังไม่มีไฟล์ ให้เลือกที่เซฟใหม่
	                }
	            }
	        });

	        // SAVE AS: บันทึกเป็นไฟล์ใหม่เสมอ
	        itemSaveAs.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                saveAsFile();
	            }
	        });

	        // EXIT: ออกจากโปรแกรม
	        itemExit.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                System.exit(0);
	            }
	        });

	        // นำเมนูย่อยใส่ลงในเมนูหลัก
	        fileMenu.add(itemNew);
	        fileMenu.add(itemOpen);
	        fileMenu.addSeparator(); // เส้นคั่น
	        fileMenu.add(itemSave);
	        fileMenu.add(itemSaveAs);
	        fileMenu.addSeparator(); // เส้นคั่น
	        fileMenu.add(itemExit);

	        // นำเมนูหลักใส่ Bar และติดตั้งลง Frame
	        menuBar.add(fileMenu);
	        setJMenuBar(menuBar);
	    }

	    // --- ฟังก์ชันช่วยสำหรับการเปิดไฟล์ ---
	    private void openFile() {
	        JFileChooser fileChooser = new JFileChooser();
	        int result = fileChooser.showOpenDialog(this);

	        if (result == JFileChooser.APPROVE_OPTION) {
	            File file = fileChooser.getSelectedFile();
	            try {
	                BufferedReader reader = new BufferedReader(new FileReader(file));
	                textArea.read(reader, null); // คำสั่งลัดในการอ่านไฟล์ลง TextArea
	                reader.close();

	                currentFile = file; // จำไฟล์นี้ไว้
	                setTitle(file.getName() + " - My Text Editor");
	            } catch (IOException ex) {
	                JOptionPane.showMessageDialog(this, "ไม่สามารถอ่านไฟล์ได้: " + ex.getMessage());
	            }
	        }
	    }

	    // --- ฟังก์ชันช่วยสำหรับการบันทึกไฟล์ (Save) ---
	    private void saveFile(File file) {
	        try {
	            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	            textArea.write(writer); // คำสั่งลัดในการเขียน TextArea ลงไฟล์
	            writer.close();
	            JOptionPane.showMessageDialog(this, "บันทึกเรียบร้อย!");
	        } catch (IOException ex) {
	            JOptionPane.showMessageDialog(this, "บันทึกไม่สำเร็จ: " + ex.getMessage());
	        }
	    }

	    // --- ฟังก์ชันช่วยสำหรับการเลือกที่บันทึก (Save As) ---
	    private void saveAsFile() {
	        JFileChooser fileChooser = new JFileChooser();
	        int result = fileChooser.showSaveDialog(this);

	        if (result == JFileChooser.APPROVE_OPTION) {
	            File file = fileChooser.getSelectedFile();

	            // ตรวจสอบว่ามี .txt หรือไม่ ถ้าไม่มีให้เติม
	            if (!file.getName().toLowerCase().endsWith(".txt")) {
	                file = new File(file.getParentFile(), file.getName() + ".txt");
	            }

	            currentFile = file; // อัปเดตไฟล์ปัจจุบัน
	            setTitle(file.getName() + " - My Text Editor");
	            saveFile(file); // เรียกฟังก์ชันบันทึก
	        }
	    }

	    public static void main(String[] args) {
	        // รันโปรแกรมใน Event Dispatch Thread (Best Practice ของ Swing)
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                new lab05_8().setVisible(true);
	            }
	        });
	    }
	}

