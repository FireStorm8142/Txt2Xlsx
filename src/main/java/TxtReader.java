import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TxtReader {

    public List<String> reader(String path){
        Path resolvedPath = Paths.get(path);
        List<String> lines = new ArrayList<>();
        try{
            lines = Files.readAllLines(resolvedPath);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return lines;
    }
}
