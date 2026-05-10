import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            TxtReader reader = new TxtReader();
            List<String> lines = reader.reader(args[0]);
            Parser parser = new Parser();
            List<Student> students = parser.parser(lines);
            ExcelGenerator generator = new ExcelGenerator();
            generator.excelGenerator(students);
            System.out.println("Excel file Generated Successfully!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
