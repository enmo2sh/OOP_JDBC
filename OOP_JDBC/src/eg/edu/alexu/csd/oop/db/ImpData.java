package eg.edu.alexu.csd.oop.db;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImpData implements Implement {
    private Facade facade = new Facade();
    @Override
    public boolean create() {
        File DataBase=new File(facade.path.getPath());
        facade.path.setdefaultDB(facade.path.getPath());
        facade.Tables.clearTables();
        if((DataBase.mkdirs())||(!DataBase.mkdirs()))
            return true;
        return false;
    }

    @Override
    public boolean drop() {
        try {
            File dir=new File(facade.path.getPath());
            if (dir.isDirectory()) {
                File[] Children=dir.listFiles();
                for(int i=0;i<Children.length;i++)
                    Children[i].delete();
            }
            Files.deleteIfExists(Paths.get(facade.path.getPath()));
            facade.path.setdefaultDB("");
            facade.Tables.SetDefaultTable("");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
