package eg.edu.alexu.csd.oop.db;

public class BasicFacade {
    public Implement creatOrDrop;
    Insert insert = new Insert();
    Update update = new Update();
    Select select = new Select();
    Delete delete = new Delete();
    SaveRead saveORread = new SaveRead();
    public Validation IsValid ;
}
