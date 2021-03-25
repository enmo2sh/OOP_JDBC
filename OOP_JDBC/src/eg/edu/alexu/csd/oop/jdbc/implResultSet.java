package eg.edu.alexu.csd.oop.jdbc;
import eg.edu.alexu.csd.oop.db.Database;
 
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
public class implResultSet implements ResultSet {
    int cursor=-1;
    boolean close=false;
    Statement stmt;
    String noC[]=null;
    Object res[][]=null;
    logging log =new logging ();
    implResultSet(Object res[][] ,String noC[], Statement stmt){
        this.res=res;
        this.cursor=cursor;
        this.close=close;
        this.stmt = stmt;
        this.noC= noC;
    }
    @Override
    public boolean absolute(int row) throws SQLException {
        if (close==false) {
        this.cursor=row;
        if (row<0)
        {
            this.cursor=res.length+row;
           
        }
        if (cursor<0 || cursor>res.length-1) {
             log.help().warning(" The Cursor Is Out Of Length Of The result Set");
             return false;
        }
         log.help().info("The Cursor Is In a True Position ");  
        return true;}
        else
        {
            log.help().info("The Result Set is Closed!");
            throw new SQLException();
        }
    }
 
    @Override
    public void afterLast() throws SQLException {
        if (close==false) {
            log.help().info(" The Cursor Is Positioned After The Last Row ");
        this.cursor = res.length;
        }else
        {
            log.help().info("The Result Set is Closed!");
            throw new SQLException();
        }
    }
    @Override
    public void beforeFirst() throws SQLException {
        if (close==false) {
            log.help().info(" The Cursor Is Positioned Before The First Row ");
        this.cursor = -1;  
        }else
        {
            log.help().info("The Result Set is Closed!");
            throw new SQLException();
        }
    }
    @Override
    public int findColumn(String columnLabel) throws SQLException {
        if (close==false) {
            int i;
        for (i=0;i<noC.length;i++)
        {
            if (noC[i].contentEquals(columnLabel)) {
                log.help().info("The Index Of specific Column Is "+(i+1));
                break;
            }
        }
        if (i==noC.length) {
            log.help().info("The specific Column isn't in the result set");
            throw new SQLException();
        }else      
        return i+1;
       
        }else
        {
            log.help().info("The Result Set is Closed!");
            throw new SQLException();
        }
    }
 
    @Override
    public boolean first() throws SQLException {
        if (close==false) {
            this.cursor = 0;
            if (res.length>0)
            {
               log.help().info("The Cursor is positioned at the first row");
                return true;
            }
            log.help().info("There are no rows in the result set");
            return false;
        }else
        {
            log.help().info("The Result Set is Closed!");
            throw new SQLException();
        }
    }
    @Override
    public int getInt(int columnIndex) throws SQLException {
        if (close==false) {
            int INT = (int) res[cursor][columnIndex-1];
            log.help().info("The integer value of the designated column in the current row is retrieved");
            return INT;
        }else
        {
            log.help().info("The Result Set is Closed!");
            throw new SQLException();
        }
    }
    @Override
    public int getInt(String columnLabel) throws SQLException {
        if (close==false) {
            int INT=0;
            for (int i=0;i<noC.length;i++)
            {
                if (noC[i].contentEquals(columnLabel)) {
                    INT = (int) res[cursor][i];
                }
            }
            log.help().info("The integer value of the designated column in the current row is retrieved");
            return INT;
        }else
        {
            log.help().info("The Result Set is Closed!");
            throw new SQLException();
        }
    }
 
    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        if (close==false) {
            ResultSetMetaData MetaData= new implResultSetMetaData(this.noC, this.res);
            log.help().info("The number, types and properties of this ResultSet are retrieved");
            return MetaData;
        }else
        {
            log.help().info("The Result Set is Closed!");
            throw new SQLException();
        }
    }
    @Override
    public Object getObject(int columnIndex) throws SQLException {
        if (close==false) {
            Object obj = res[cursor][columnIndex-1];
            log.help().info("The value of the designated column in the current row is retrieved");
            return obj;
        }else
        {
            log.help().info("The Result Set is Closed!");
            throw new SQLException();
        }
    }
    @Override
    public Statement getStatement() throws SQLException {
        if (close==false) {
            log.help().info("The Statement object that produced this ResultSet object is retrieved");
            return this.stmt;
           
        }else
        {
            log.help().info("The Result Set is Closed!");
            throw new SQLException();
        }
    }
    @Override
    public String getString(int columnIndex) throws SQLException {
        if (close==false) {
            String Str = (String) res[cursor][columnIndex-1];
            log.help().info("The string value of the designated column in the current row is retrieved");
            return Str;
        }else
        {
            log.help().info("The Result Set is Closed!");
            throw new SQLException();
        }
    }
    @Override
    public String getString(String columnLabel) throws SQLException {
        if (close==false) {
            String Str = null;
            for (int i=0;i<noC.length;i++)
            {
                if (noC[i].contentEquals(columnLabel))
                    Str= (String) res[cursor][i];
            }
            log.help().info("The string value of the designated column in the current row is retrieved");
            return Str;
        }else
        {
            log.help().info("The Result Set is Closed!");
            throw new SQLException();
        }
    }
    @Override
    public boolean isAfterLast() throws SQLException {
        if (close==false) {
            if (cursor==res.length) {
                log.help().info("The cursor is after last row");
                return true;
            }
            log.help().info("The cursor isn't after the last row");
            return false;
        }else
        {
            log.help().info("The Result Set is Closed!");
            throw new SQLException();
        }
    }
    @Override
    public boolean isBeforeFirst() throws SQLException {
        if (close==false) {
            if (cursor==-1) {
                log.help().info("The cursor is before first row");
                return true;
            }
            log.help().info("The cursor isn't before first row");
            return false;
        }else
        {
            log.help().info("The Result Set is Closed!");
            throw new SQLException();
        }
    }
    @Override
    public boolean isClosed() throws SQLException {
        if(close==true) {
            log.help().info("The ResultSet is closed!");
            return true;
        }
        log.help().info("The ResultSet isn't closed!");
        return false;
    }
    @Override
    public boolean isFirst() throws SQLException {
        if (close==false) {
            if (cursor==0) {
                log.help().info("The cursor is at the first row");
                return true;
            }
            log.help().info("The cursor isn't at the first row");
            return false;
        }else
        {
            log.help().info("The Result Set is Closed!");
            throw new SQLException();
        }
    }
    @Override
    public boolean isLast() throws SQLException {
        if (close==false) {
            if (cursor ==res.length-1 ) {
                log.help().info("The cursor is at the last row");
                return true;
            }
            log.help().info("The cursor isn't at the last row");
            return false;
        }else
        {
            log.help().info("The Result Set is Closed!");
            throw new SQLException();
        }
    }
    @Override
    public boolean last() throws SQLException {
        if (close==false) {
            cursor=res.length-1;
            if (res.length>0) {
                log.help().info("The cursor is positioned at the last row");
                return true;
            }
            log.help().info("There are no rows in the result set");
            return false;
        }else
        {
            log.help().info("The Result Set is Closed!");
            throw new SQLException();
        }
    }
 
 
    @Override
    public void close() throws SQLException {
        close=true;
        log.help().info("The result set is closed!");
    }
 
    @Override
    public boolean next() throws SQLException {
        if (close==false) {
            cursor = cursor + 1 ;
            if (this.isAfterLast()) {
                log.help().info("There are no more rows in the result set");
                return false;
            }
            log.help().info("The cursor is moved to the next row");
            return true;
        }else
        {
            log.help().info("The Result Set is Closed!");
            throw new SQLException();
        }
    }
    @Override
    public boolean previous() throws SQLException {
        if (close==false) {
            cursor = cursor -1 ;
            if (this.isBeforeFirst()) {
                log.help().info("The cursor is positioned before the first row");
                return false;
            }
            log.help().info("The cursor is moved to the previous row");
            return true;
        }else
        {
            log.help().info("The Result Set is Closed!");
            throw new SQLException();
        }
    }
 
 
 
 
/***************************************************************/
    @Override
    public boolean relative(int rows) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Reader getCharacterStream(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Reader getCharacterStream(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public long getLong(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public float getFloat(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public double getDouble(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Date getDate(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Time getTime(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public InputStream getAsciiStream(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public InputStream getBinaryStream(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public long getLong(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public float getFloat(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public double getDouble(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public byte[] getBytes(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Date getDate(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Time getTime(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public InputStream getAsciiStream(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public InputStream getUnicodeStream(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public InputStream getBinaryStream(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void clearWarnings() throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public String getCursorName() throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Object getObject(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Ref getRef(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Array getArray(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Ref getRef(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Blob getBlob(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Clob getClob(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Array getArray(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Date getDate(String columnLabel, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Time getTime(String columnLabel, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public URL getURL(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public URL getURL(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateRef(int columnIndex, Ref x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateRef(String columnLabel, Ref x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateBlob(int columnIndex, Blob x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateBlob(String columnLabel, Blob x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateClob(int columnIndex, Clob x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateClob(String columnLabel, Clob x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateArray(int columnIndex, Array x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateArray(String columnLabel, Array x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public RowId getRowId(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public RowId getRowId(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public int getHoldability() throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public byte getByte(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public short getShort(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public boolean getBoolean(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public byte getByte(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public short getShort(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateNString(int columnIndex, String nString) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateNString(String columnLabel, String nString) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public NClob getNClob(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public NClob getNClob(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public String getNString(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public String getNString(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public boolean wasNull() throws SQLException {
        throw new UnsupportedOperationException();
    }
    @Override
    public int getRow() throws SQLException {
        throw new UnsupportedOperationException();
    }
 
 
    @Override
    public void setFetchDirection(int direction) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public int getFetchDirection() throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void setFetchSize(int rows) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public int getFetchSize() throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public int getType() throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public int getConcurrency() throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public boolean rowUpdated() throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public boolean rowInserted() throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public boolean rowDeleted() throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateNull(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateBoolean(int columnIndex, boolean x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateByte(int columnIndex, byte x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateShort(int columnIndex, short x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateInt(int columnIndex, int x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateLong(int columnIndex, long x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateFloat(int columnIndex, float x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateDouble(int columnIndex, double x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateString(int columnIndex, String x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateBytes(int columnIndex, byte[] x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateDate(int columnIndex, Date x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateTime(int columnIndex, Time x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateObject(int columnIndex, Object x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateNull(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateBoolean(String columnLabel, boolean x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateByte(String columnLabel, byte x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateShort(String columnLabel, short x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateInt(String columnLabel, int x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateLong(String columnLabel, long x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateFloat(String columnLabel, float x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateDouble(String columnLabel, double x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateString(String columnLabel, String x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateBytes(String columnLabel, byte[] x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateDate(String columnLabel, Date x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateTime(String columnLabel, Time x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateObject(String columnLabel, Object x) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void insertRow() throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void updateRow() throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void deleteRow() throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void refreshRow() throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void cancelRowUpdates() throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void moveToInsertRow() throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void moveToCurrentRow() throws SQLException {
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