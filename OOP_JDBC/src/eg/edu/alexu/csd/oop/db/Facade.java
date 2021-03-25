package eg.edu.alexu.csd.oop.db;

public class Facade {
    Parser pars;
    Factory factory = new Factory();
    public Path path = new Path();
    Base base = new Base();
    public tablesName Tables = new tablesName();
    GetQuery query = new GetQuery();
    ModifyQuery queryOperations = new ModifyQuery();
    whereCondition Where = new whereCondition();
    Order order = new Order ();
    public XSDinfo xsd = new XSDinfo();
}
