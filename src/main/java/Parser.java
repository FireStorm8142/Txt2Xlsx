import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class Parser {
    public List<Student> parser(List<String> lines){
        Subject subject;
        Student student;
        List<Student> students = new ArrayList<>();

        //main loop that detects roll no., if not detected, skip line
        for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++){
            String[] tokens = lines.get(lineIndex).trim().split("\\s+");
            if (tokens.length == 0) continue;
            if (tokens[0].matches("\\d{8}")){
                String roll = tokens[0];
                String gender = tokens[1];
                int subjectIndex = 0;
                int internalGradeIndex = 0;
                List<Subject> subjects = new ArrayList<>();
                StringBuilder name = new StringBuilder();

                //this loop keeps reading tokens (names) until subject code is detected
                for (int i=2; i<tokens.length; i++){
                    if (tokens[i].matches("\\d{3}")) {
                        subjectIndex = i;
                        break;
                    }
                    name.append(tokens[i]).append(" ");
                }

                //this loop keeps reading tokens (subject codes) until internal grades are detected
                List<String> subjectCodes = new ArrayList<>();
                for (; subjectIndex<tokens.length; subjectIndex++){
                    if (tokens[subjectIndex].matches("\\d{3}")) subjectCodes.add(tokens[subjectIndex]);
                    else{
                        internalGradeIndex = subjectIndex;
                        break;
                    }
                }

                //this loop keeps reading tokens (internal grades) until result is detected
                List<String> internalGradeCodes = new ArrayList<>();
                for (; internalGradeIndex<tokens.length; internalGradeIndex++){
                    if (tokens[internalGradeIndex].matches("[A-F][1-2]?")) internalGradeCodes.add(tokens[internalGradeIndex]);
                    else break;
                }

                //store result
                String result = tokens[internalGradeIndex];
                lineIndex++;


                //Parsing Marks, 2nd Line
                if (lineIndex >= lines.size()) continue;
                String[] marksLine = lines.get(lineIndex).trim().split("\\s+");
                List<String> marks = new ArrayList<>();
                List<String> grades = new ArrayList<>();
                for (int i=0; i<marksLine.length; i++){
                    if (marksLine[i].matches("\\d{3}")) marks.add(marksLine[i]);
                    else if (marksLine[i].matches("[A-F][1-2]?")) grades.add(marksLine[i]);
                    else break;
                }

                //check for malformed data (Mismatch b/w number of sub. codes and parsed grades/marks)
                if (marks.size() != subjectCodes.size() || grades.size() != subjectCodes.size()) {
                    System.out.println("Data mismatch, skipping row");
                    continue;
                }

                //create subject objects
                for (int i=0; i<subjectCodes.size(); i++){
                    String tempSubCode = subjectCodes.get(i);
                    String tempMarks = marks.get(i);
                    String tempGrades = grades.get(i);
                    subject = new Subject(tempSubCode, tempMarks, tempGrades);
                    subjects.add(subject);
                }

                //finally, create student object with list of subjects mapped
                student = new Student(roll, gender, name.toString().trim(), result, internalGradeCodes, subjects);
                students.add(student);
            }
        }
        return students;
    }
}
