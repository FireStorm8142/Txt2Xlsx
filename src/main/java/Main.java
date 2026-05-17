import javax.swing.*;
import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Txt2Xlsx");
        JButton button = new JButton("Choose TXT File");
        button.addActionListener(_ -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                try {
                    TxtReader reader = new TxtReader();
                    Parser parser = new Parser();
                    ExcelGenerator generator = new ExcelGenerator();

                    List<String> lines = reader.reader(file.getAbsolutePath()); //read all lines from txt file
                    List<Student> students = parser.parser(lines); //parse all lines and create tokens
                    generator.masterGenerator(students); //generate excel sheets
                    generator.subjectStats(students);
                    generator.percentageRange(students);
                    generator.top10Science(students);
                    generator.top10Commerce(students);
                    generator.finalWrite();

                    JOptionPane.showMessageDialog(frame, "Excel generated successfully!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });
        frame.add(button);
        frame.setSize(300, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}