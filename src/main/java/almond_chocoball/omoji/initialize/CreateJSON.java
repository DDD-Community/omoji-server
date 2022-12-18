package almond_chocoball.omoji.initialize;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateJSON {
    String fileName;
    String fileDetail;

    public CreateJSON(String fileName, String fileDetail){
        this.fileName=fileName;
        this.fileDetail=fileDetail;
    }

    private Path getCurrentPath(){
        return Paths.get(System.getProperty("user.dir"));
    }

    public Path setPathToResource() {
        return Paths.get(this.getCurrentPath().toString(), "src", "main", "resources", fileName);
    }

}
