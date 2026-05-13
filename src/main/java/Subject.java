public class Subject {
    String code;
    String marks;
    String grade;
    Subject (String code, String marks, String grade) {
        this.code = code;
        this.marks = marks;
        this.grade = grade;
    }

    public int getMarks(){
        if (marks.equals("AB")) return 0;
        else return Integer.parseInt(marks);
    }
}
