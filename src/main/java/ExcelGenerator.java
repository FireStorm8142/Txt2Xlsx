import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExcelGenerator {
    public void excelGenerator(List<Student> students) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Results");
        int rowNum = 0;
        //Mapping subject codes to subject names
        final Map<String, String> subjectName = Map.ofEntries(Map.entry("301", "English Core"),
            Map.entry("041", "Mathematics"), Map.entry("042", "Physics"),
            Map.entry("043", "Chemistry"), Map.entry("044", "Biology"),
            Map.entry("083", "Computer Science"), Map.entry("030", "Economics"),
            Map.entry("054", "Business Studies"), Map.entry("055", "Accountancy"),
            Map.entry("065", "Informatics Practices"), Map.entry("241", "Applied Mathematics"),
            Map.entry("812", "Artificial Intelligence"));

        // Header Row
        Row header = sheet.createRow(rowNum++);
        header.createCell(0).setCellValue("Roll No");
        header.createCell(1).setCellValue("Student Name");
        header.createCell(2).setCellValue("Gender");
        header.createCell(3).setCellValue("Result");

        int subjectCol = 4;
        for (int i = 1; i <= 5; i++) {
            header.createCell(subjectCol++).setCellValue("Sub " + i + " Code");
            header.createCell(subjectCol++).setCellValue("Sub " + i + " Name");
            header.createCell(subjectCol++).setCellValue("Sub " + i + " Marks");
            header.createCell(subjectCol++).setCellValue("Sub " + i + " Grade");
        }

        // Student Rows
        for (Student student : students) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(student.rollNo);
            row.createCell(1).setCellValue(student.name);
            row.createCell(2).setCellValue(student.gender);
            row.createCell(3).setCellValue(student.result);

            int col = 4;
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

        FileOutputStream fos = new FileOutputStream("Results.xlsx");
        workbook.write(fos);
        fos.close();
        workbook.close();
    }
}