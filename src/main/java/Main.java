import java.util.List;

public class Main {
    public static void main(String[] args) {
        TxtReader reader = new TxtReader();
        List<String> lines = reader.reader(args[0]);
        Parser parser = new Parser();
        List<Student> students = parser.parser(lines);
    }
}
