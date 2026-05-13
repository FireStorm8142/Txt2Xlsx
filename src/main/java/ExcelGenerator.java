import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

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

    public void masterGenerator(List<Student> students) {
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
                    student.stream = "SCIENCE";
                    break;
                }
                else if(sub.code.equals("030")) {
                    row.createCell(col++).setCellValue("COMMERCE");
                    student.stream = "COMMERCE";
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
        //This is for formatting the mean score to 2 decimal place's
        CellStyle decimalStyle = workbook.createCellStyle();
        DataFormat formatter = workbook.createDataFormat();
        decimalStyle.setDataFormat(formatter.getFormat("0.00"));
        Cell cell;
        String[] grades = {"A1", "A2", "B1", "B2", "C1", "C2", "D1", "D2", "E"};
        //-----Subject Performance Index and Average Page-----//
        //Header Row
        Row header = sheet.createRow(rowNum++);
        header.createCell(0).setCellValue("Code");
        header.createCell(1).setCellValue("Subject Name");
        header.createCell(2).setCellValue("Appeared");
        int i = 3;
        for (String grade : grades) {
            header.createCell(i++).setCellValue(grade);
        }
        header.createCell(i++).setCellValue("Highest");
        header.createCell(i).setCellValue("Mean Score");

        //Student Rows
        for (String key : subjectName.keySet()) {
            //Individual stats for each Subject
            int highest = 0, appeared = 0, totalMarks = 0;
            Map<String, Integer> gradeCount = new HashMap<>();
            int col = 0;
            Row row = sheet.createRow(rowNum++);
            row.createCell(col++).setCellValue(key);
            row.createCell(col++).setCellValue(subjectName.get(key));
            //This loop iterates through each subject of all the students and aggregates the data
            for (Student student : students) {
                for (Subject sub : student.subjects) {
                    if (sub.code.equals(key)) {
                        appeared++;
                        highest = Math.max(highest, Integer.parseInt(sub.marks));
                        totalMarks += Integer.parseInt(sub.marks);
                        gradeCount.put(sub.grade, gradeCount.getOrDefault(sub.grade, 0)+1);
                    }
                }
            }
            double meanScore = (double) totalMarks / appeared;
            row.createCell(col++).setCellValue(Integer.toString(appeared));
            for (i = 0; i<grades.length; i++) row.createCell(col++).setCellValue(Integer.toString(gradeCount.getOrDefault(grades[i], 0)));
            row.createCell(col++).setCellValue(Integer.toString(highest));
            cell = row.createCell(col);
            cell.setCellValue(meanScore);
            cell.setCellStyle(decimalStyle);
        }
        // Auto-size columns
        for (i = 0; i < 15; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public void percentageRange(List<Student> students) {
        int rowNum = 0;
        sheet = workbook.createSheet("Percentage Range Dist");
        //This merges 3 columns into 1 in the header row
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 3));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 6));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 9));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 10, 12));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 13, 15));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 16, 18));
        //Centering header columns
        CellStyle centerStyle = workbook.createCellStyle();
        centerStyle.setAlignment(HorizontalAlignment.CENTER);
        centerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //-----Percentage Range Distance Page-----//
        //Header Row
        Row header = sheet.createRow(rowNum++);
        header.createCell(0).setCellValue("  Stream/Category  ");
        header.createCell(1).setCellValue("  0-32.9% (Fail)  ");
        header.createCell(4).setCellValue("  33-44.9%  ");
        header.createCell(7).setCellValue("  45-59.9%  ");
        header.createCell(10).setCellValue("  60-74.9%  ");
        header.createCell(13).setCellValue("  75-89.9%  ");
        header.createCell(16).setCellValue("  90-100%  ");
        header = sheet.createRow(rowNum++);
        header.createCell(0).setCellValue("    ");
        for (int i = 1; i < 18; i++) {
            header.createCell(i++).setCellValue("  B  ");
            header.createCell(i++).setCellValue("  G  ");
            header.createCell(i).setCellValue("  T  ");
        }

        //Student Rows
        //3-D array to store student Stream, Marks and Gender
        int[][][] ranges = new int[3][6][3];
        for (Student  s : students) {
            double percentage = s.getPercentage();
            int Index;
            int Stream;
            if (percentage < 33) Index = 0;
            else if (percentage < 45) Index = 1;
            else if (percentage < 60) Index = 2;
            else if (percentage < 75) Index = 3;
            else if (percentage < 90) Index = 4;
            else Index = 5;

            if (s.stream.equals("SCIENCE")) Stream = 0;
            else Stream = 1;

            ranges[Stream][Index][2]++;
            if (s.gender.equalsIgnoreCase("M")) ranges[Stream][Index][0]++;
            else ranges[Stream][Index][1]++;
        }
        //Total Sum
        for (int i = 0; i < 6; i++){
            ranges[2][i][0] = ranges[0][i][0] + ranges[1][i][0];
            ranges[2][i][1] = ranges[0][i][1] + ranges[1][i][1];
            ranges[2][i][2] = ranges[0][i][2] + ranges[1][i][2];
        }

        String[] streamName = {"SCIENCE", "COMMERCE", "OVERALL"};
        for (int i = 0; i<streamName.length; i++) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(streamName[i]);

            int col = 1;
            for (int r = 0; r < 6; r++){
                row.createCell(col++).setCellValue(ranges[i][r][0]);
                row.createCell(col++).setCellValue(ranges[i][r][1]);
                row.createCell(col++).setCellValue(ranges[i][r][2]);
            }
        }
        // Auto-size columns
        for (int i = 0; i < 20; i++) {
            sheet.autoSizeColumn(i);
        }
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