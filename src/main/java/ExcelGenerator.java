import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelGenerator {
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet;
    FileOutputStream fos;
    //Mapping subject codes to subject names
    final Map<String, String> subjectName = Map.ofEntries(Map.entry("301", "English Core"),
            Map.entry("041", "Mathematics"), Map.entry("042", "Physics"),
            Map.entry("043", "Chemistry"), Map.entry("044", "Biology"),
            Map.entry("083", "Computer Science"), Map.entry("030", "Economics"),
            Map.entry("054", "Business Studies"), Map.entry("055", "Accountancy"),
            Map.entry("065", "Informatics Practices"), Map.entry("241", "Applied Mathematics"),
            Map.entry("812", "Artificial Intelligence"));

    public void masterGenerator(List<Student> students) throws IOException {
        int rowNum = 0;
        sheet = workbook.createSheet("Master");
        //-----ALl Stats page-----//
        // Header Row
        Row header = sheet.createRow(rowNum++);
        header.createCell(0).setCellValue("Roll No");
        header.createCell(1).setCellValue("Student Name");
        header.createCell(2).setCellValue("Gender");
        header.createCell(3).setCellValue("Stream");
        header.createCell(4).setCellValue("Result");

        int subjectCol = 5;
        for (int i = 1; i <= 5; i++) {
            header.createCell(subjectCol++).setCellValue("Sub " + i + " Code");
            header.createCell(subjectCol++).setCellValue("Sub " + i + " Name");
            header.createCell(subjectCol++).setCellValue("Sub " + i + " Marks");
            header.createCell(subjectCol++).setCellValue("Sub " + i + " Grade");
        }

        // Student Rows
        for (Student student : students) {
            int col = 0;
            Row row = sheet.createRow(rowNum++);
            row.createCell(col++).setCellValue(student.rollNo);
            row.createCell(col++).setCellValue(student.name);
            row.createCell(col++).setCellValue(student.gender);
            //Set Stream according to subject opted by Student
            for (Subject sub : student.subjects) {
                if (sub.code.equals("041") || sub.code.equals("042")) {
                    row.createCell(col++).setCellValue("SCIENCE");
                    break;
                }
                else if(sub.code.equals("030")) {
                    row.createCell(col++).setCellValue("COMMERCE");
                    break;
                }
            }
            row.createCell(col++).setCellValue(student.result);
            for (Subject s : student.subjects) {
                row.createCell(col++).setCellValue(s.code);
                row.createCell(col++).setCellValue(subjectName.getOrDefault(s.code, "Subject Not Mapped"));
                row.createCell(col++).setCellValue(s.marks);
                row.createCell(col++).setCellValue(s.grade);
            }
        }
        // Auto-size columns
        for (int i = 0; i < 20; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public void subjectStats(List<Student> students) {
        int rowNum = 0;
        sheet = workbook.createSheet("Subject PI & Avg");
        String[] grades = {"A1", "A2", "B1", "B2", "C1", "C2", "D1", "D2", "E"};
        //-----Subject Performance Index and Average Page-----//
        //Header Row
        Row header = sheet.createRow(rowNum++);
        header.createCell(0).setCellValue("Code");
        header.createCell(1).setCellValue("Subject Name");
        header.createCell(2).setCellValue("Appeared");
        header.createCell(3).setCellValue("Result");
        int i = 4;
        for (String grade : grades) {
            header.createCell(i++).setCellValue(grade);
        }
        header.createCell(i++).setCellValue("Highest");
        header.createCell(i++).setCellValue("Mean Score");
        header.createCell(i).setCellValue("PI %");

        //Student Rows
        int highest = 0, appeared = 0, totalMarks = 0;
        Map<String, Integer> gradeCount = new HashMap<>();
        for (String key : subjectName.keySet()) {
            int col = 0;
            Row row = sheet.createRow(rowNum++);
            row.createCell(col++).setCellValue(key);
            row.createCell(col++).setCellValue(subjectName.get(key));

        }
    }

    public void percentageRange(List<Student> students) {
        int rowNum = 0;
        sheet = workbook.createSheet("Percentage Range Dist");
    }

    public void top10Science(List<Student> students) {
        int rowNum = 0;
        sheet = workbook.createSheet("Top 10 Science");
    }

    public void top10Commerce(List<Student> students) {
        int rowNum = 0;
        sheet = workbook.createSheet("Top 10 Commerce");
    }

    public void finalWrite() throws IOException{
        fos = new FileOutputStream("Results.xlsx");
        workbook.write(fos);
        fos.close();
        workbook.close();
    }
}