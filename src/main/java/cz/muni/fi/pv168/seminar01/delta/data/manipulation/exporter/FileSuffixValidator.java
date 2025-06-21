package cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSuffixValidator {

    public static Path checkFileSuffix(Path path, FileFormat format){
        if (path.toString().endsWith(format.getSuffix())){
            return path;
        }
        return Paths.get(path + format.getSuffix());
    }
}
