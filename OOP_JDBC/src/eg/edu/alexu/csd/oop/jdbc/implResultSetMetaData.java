package eg.edu.alexu.csd.oop.jdbc;



import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

public class implResultSetMetaData implements ResultSetMetaData {
    boolean close=false;
    String defaultTB="";
    String noC[]=null;
    Object res[][]=null;
    logging log =new logging ();
    implResultSetMetaData(String noC[], Object[][] res){
        this.noC=noC;
        this.res=res;
        this.close=close;
    }
    @Override
    public int getColumnCount() throws SQLException {
        if(close==false)
        {
        	log.help().info(" Get the Column Count which is "+ noC.length );
        	return noC.length;
        }
            
        else
        {
        	log.help().warning("Deosn`t Get the Column Count because the ResultSetMetaData is Closed ");
        	 throw new SQLException();
        }
           
    }

    @Override
    public String getColumnName(int column) throws SQLException {
        if(close==false) {
        	log.help().info(" Get the Column Name which is "+ noC[column-1] );
        	 return noC[column-1];
        }   
        else {
        	log.help().warning("Deosn`t Get the Column Name because the ResultSetMetaData is Closed ");
        	throw new SQLException();
        }
            
    }

    @Override
    public int getColumnType(int column) throws SQLException {
        if(close==false) {
            if (((String)res[1][ column-1]).contains("'"))
                return Types.VARCHAR;
            else
                return Types.INTEGER;
        }
        else
            throw new SQLException();
    }
    @Override
    public String getTableName(int column) throws SQLException {
        return defaultTB;
    }
    @Override
    public String getColumnLabel(int column) throws SQLException {
        if(close==false) {
        	 log.help().info(" Get the Column Label which is "+ noC[column-1] );
        	 return noC[column-1];
        }
           
        else {
        	log.help().warning("Deosn`t Get the Column Label because the ResultSetMetaData is Closed ");
        	 throw new SQLException();
        }
           
    }







    /***************************************************************/
    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSearchable(int column) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCurrency(int column) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int isNullable(int column) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSigned(int column) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getColumnDisplaySize(int column) throws SQLException {
        throw new UnsupportedOperationException();
    }


    @Override
    public String getSchemaName(int column) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getPrecision(int column) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getScale(int column) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isReadOnly(int column) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWritable(int column) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDefinitelyWritable(int column) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getCatalogName(int column) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
