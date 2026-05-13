import java.util.List;

public class Student {
    String rollNo;
    String gender;
    String name;
    String result;
    String stream;
    List<String> internalGrades;
    List<Subject> subjects;
    public Student (String rollNo, String gender, String name, String result, List<String> internalGrades, List<Subject> subjects) {
        this.rollNo = rollNo;
        this.gender = gender;
        this.name = name;
        this.result = result;
        this.internalGrades = internalGrades;
        this.subjects = subjects;
    }

    public double getPercentage(){
        double obtained = 0;
        for (Subject s : subjects) obtained += s.getMarks();
        return obtained / subjects.size();
    }
}
