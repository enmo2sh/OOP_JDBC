package eg.edu.alexu.csd.oop.jdbc;

import java.sql.SQLException;

public interface UI {
    public void print();
    public void call(int ch) throws SQLException;
}
