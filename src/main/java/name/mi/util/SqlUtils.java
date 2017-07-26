package name.mi.util;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlUtils
{
    public static final int
        BATCH_UPDATE_LIMIT = 100;

    public static final Pattern
        DEFAULT_KEY_PATTERN  = Pattern.compile("^-- #\\[(.*)\\]\\:$");

    public static final Pattern
        DEFAULT_REPLACEMENT_PATTERN = Pattern.compile("-- \\[\\[(.*)\\]\\]");

    public static final String
        DEFAULT_REPLACEMENT_PATTERN_PREFIX = "-- \\[\\[",
        DEFAULT_REPLACEMENT_PATTERN_SUFFIX = "\\]\\]";

    public static final String
        DEFAULT_QUERY_FILE_EXTENSION = ".sql",
        DEFAULT_LINE_COMMENT = "#";

    /**
     * default constructor, should not be called anywhere
     * private
     */
    private SqlUtils()
    {
        super();
        throw new IllegalStateException();
    }

    public static void executeBatch(
        PreparedStatement iPreparedStatement,
        long[]            iIDs,
        int               iIDArrayStartIndex,
        List<Long>        iList
    )
        throws SQLException
    {
        int[]
            vResults = iPreparedStatement.executeBatch();

        int
            nResults = vResults == null ? 0 : vResults.length;

        for (int i = 0; i < nResults; ++i)
        {
            if(vResults[i] > 0)
            {
                iList.add(iIDs[iIDArrayStartIndex + i]);
            }
        }
    }

    /**
     * open a class resource as stream
     * @param iClass
     * @param iExtension
     * @return
     * @throws java.io.IOException
     */
    public static InputStream openClassResource(
        Class  iClass,
        String iExtension
    )
        throws IOException
    {
        if(iClass == null)
        {
            throw new IllegalArgumentException(
                "SqlUtils.openClassResource: iClass is null"
            );
        }

        if(iExtension == null)
        {
            throw new IllegalArgumentException(
                "SqlUtils.openClassResource: iExtension is null"
            );
        }

        String
            vResourcePath = iClass.getSimpleName() + ((iExtension.isEmpty() || iExtension.startsWith(".")) ? iExtension : ("." + iExtension));

        URL
            vResourceUrl = iClass.getResource(vResourcePath);

        if(vResourceUrl != null)
        {
            return vResourceUrl.openStream();
        }

        throw new IOException(
            "SqlUtils.openClassResource: cannot open resource ["+vResourcePath+"] for class ["+iClass+"]"
        );
    }

    public static String doReplace(
        String iSource,
        String iPatternCore,
        String iReplacementContent
    ) throws IOException
    {
        Pattern vPattern = Pattern.compile(DEFAULT_REPLACEMENT_PATTERN_PREFIX + iPatternCore + DEFAULT_REPLACEMENT_PATTERN_SUFFIX);
        Matcher vMatcher = vPattern.matcher(iSource);
        String vTmpResult = vMatcher.replaceFirst(iReplacementContent);
        return vTmpResult;
    }

    /**
     * load properties from reader
     * @param iPropertyReader
     * @param iKeyPattern
     * @param iLineComment
     * @param iSkipEmptyLines
     * @return
     * @throws java.io.IOException
     */
    public static Map<String, String> loadProperties(
        Reader iPropertyReader,
        Pattern iKeyPattern,
        String  iLineComment,
        boolean iSkipEmptyLines
    )
        throws IOException
    {
        if(iPropertyReader == null)
        {
            return null;
        }

        BufferedReader
            vReader = iPropertyReader instanceof BufferedReader ? (BufferedReader) iPropertyReader : new BufferedReader(iPropertyReader);

        Map<String, String>
            vResourceMap = new HashMap<String, String>();

        String
            vResourceKey = null,
            vLine;

        StringBuilder
            vValueBuilder = new StringBuilder();

        while((vLine = vReader.readLine()) != null)
        {
            if(iLineComment != null && vLine.startsWith(iLineComment))
            {
                continue;
            }

            if(iSkipEmptyLines && vLine.trim().length() == 0)
            {
                continue;
            }

            Matcher
                wMatcher = iKeyPattern.matcher(vLine);

            if(wMatcher.matches())
            {
                if(vResourceKey != null)
                {
                    vResourceMap.put(
                        vResourceKey,
                        vValueBuilder.toString()
                    );
                }

                int
                    wGroupCount = wMatcher.groupCount();

                vResourceKey = wGroupCount <= 0 ? null : wMatcher.group(1);

                if(vResourceKey == null || (vResourceKey = vResourceKey.trim()).length() == 0)
                {
                    throw new IOException(
                        "SqlUtils.loadProperties: bad format for vResourceKey " + vResourceKey
                    );
                }

                vValueBuilder.setLength(0);

                for(int n = 2; n <= wGroupCount; ++n)
                {
                    vValueBuilder.append(wMatcher.group(n));
                }
            }
            else
            if(vResourceKey == null)
            {
                throw new IOException(
                    "SqlUtils.loadProperties: vResourceKey is null"
                );
            }
            else
            {
                if(vValueBuilder.length() > 0)
                {
                    vValueBuilder.append('\n');
                }
                vValueBuilder.append(vLine);
            }
        }

        if(vResourceKey != null)
        {
            vResourceMap.put(
                vResourceKey,
                vValueBuilder.toString()
            );
        }

        return vResourceMap;
    }

    /**
     * load properties for a given class
     * @param iClass
     * @param iExt
     * @param iKeyPattern
     * @param iLineComment
     * @param iSkipEmptyLines
     * @return
     * @throws java.io.IOException
     */
    public static Map<String, String> loadClassProperties(
        Class   iClass,
        String  iExt,
        Pattern iKeyPattern,
        String  iLineComment,
        boolean iSkipEmptyLines
    )
        throws IOException
    {
        InputStream
            vResourceStream = openClassResource(iClass, iExt);

        try
        {
            BufferedReader
                vReader = new BufferedReader(new InputStreamReader(vResourceStream));

            Map<String, String>
                vPropertyMap = loadProperties(
                vReader,
                iKeyPattern,
                iLineComment,
                iSkipEmptyLines
            );

            if(vPropertyMap == null)
            {
                throw new IllegalStateException(
                    "SqlUtils.loadClassProperties: resulting properties is null"
                );
            }

            return vPropertyMap;
        }
        finally
        {
            if (vResourceStream != null)
            {
                try
                {
                    vResourceStream.close();
                }
                catch (Exception ignored)
                {
                }
            }
        }
    }

    /**
     * load the query properties for a given class
     * @param iClass
     * @return Map
     * @throws IllegalStateException .
     */
    public static Map<String, String> loadQueryProperties(
        Class iClass
    )
        throws IllegalStateException
    {
        try
        {
            return
                loadClassProperties(
                    iClass,
                    DEFAULT_QUERY_FILE_EXTENSION,
                    DEFAULT_KEY_PATTERN,
                    DEFAULT_LINE_COMMENT,
                    true
                );
        }
        catch(Exception ex)
        {
            throw new IllegalStateException(
                "SqlUtils.loadQueryProperties: failed loading required sql resource file for class " + iClass,
                ex
            );
        }
    }

    /**
     * @param iConnection
     */
    public static final void close(
        Connection iConnection
    )
    {
        if(iConnection == null)
        {
            return;
        }

        try
        {
            iConnection.close();
        }
        catch(Exception ignored)
        {
            // ignored
        }

        return;
    }

    /**
     * @param iStatement
     */
    public static final void close(
        Statement iStatement
    )
    {
        if(iStatement == null)
        {
            return;
        }

        try
        {
            iStatement.close();
        }
        catch(Exception ignored)
        {
            // ignored
        }

        return;
    }


    /**
     * @param iResultSet
     */
    public static final void close(
        ResultSet iResultSet
    )
    {
        if(iResultSet == null)
        {
            return;
        }

        try
        {
            iResultSet.close();
        }
        catch(Exception ignored)
        {
            // ignored
        }

        return;
    }


    /**
     * @param iCloseables @Nullable
     */
    public static final void closeAll(
        Object... iCloseables
    )
    {
        if(iCloseables == null)
        {
            return;
        }

        for(Object wCloseable : iCloseables)
        {
            if(wCloseable instanceof ResultSet)
            {
                close((ResultSet) wCloseable);
                continue;
            }

            if(wCloseable instanceof Statement)
            {
                close((Statement) wCloseable);
                continue;
            }

            if(wCloseable instanceof Connection)
            {
                close((Connection) wCloseable);
                continue;
            }

            if(wCloseable == null)
            {
                continue;
            }

            throw new IllegalArgumentException(
                "SqlUtils.closeAll: cannot close object of type = ["+wCloseable.getClass().getName()+"]"
            );
        }

        return;
    }

    /**
     * @param iStatement .
     * @return id
     * @throws java.sql.SQLException .
     */
    public static final long getFirstID(
        Statement iStatement
    )
        throws SQLException
    {
        if(iStatement == null)
        {
            throw new IllegalStateException(
                "SqlUtils.getFirstID: iStatement is null"
            );
        }

        ResultSet
            vResultSet = null;

        try
        {
            vResultSet = iStatement.getGeneratedKeys();

            if(vResultSet == null)
            {
                return -1L;
            }

            if(vResultSet.next())
            {
                return vResultSet.getLong(1);
            }
        }
        finally
        {
            close(vResultSet);
        }

        return -1L;
    }

    /**
     * @param iStatement .
     * @return id
     * @throws java.sql.SQLException .
     */
    public static final long getLastID(
        Statement iStatement
    )
        throws SQLException
    {
        if(iStatement == null)
        {
            throw new IllegalStateException(
                "SqlUtils.getLastID: iStatement is null"
            );
        }

        ResultSet
            vResultSet = null;

        try
        {
            vResultSet = iStatement.getGeneratedKeys();

            if(vResultSet == null)
            {
                return -1L;
            }

            long
                vId = -1L;

            while(vResultSet.next())
            {
                vId = vResultSet.getLong(1);
            }

            return vId;
        }
        finally
        {
            close(vResultSet);
        }
    }

    /**
     * @param iStatement .
     * @return id
     * @throws java.sql.SQLException .
     */
    public static final long[] getAllIDs(
        Statement iStatement
    )
        throws SQLException
    {
        if(iStatement == null)
        {
            throw new IllegalStateException(
                "SqlUtils.getAllIDs: iStatement is null"
            );
        }

        ResultSet
            vResultSet = null;

        List<Long>
            vIdList = null;

        try
        {
            vResultSet = iStatement.getGeneratedKeys();

            if(vResultSet == null)
            {
                return null;
            }

            vIdList = new LinkedList<Long>();

            if(vResultSet.next())
            {
                vIdList.add(vResultSet.getLong(1));
            }
        }
        finally
        {
            close(vResultSet);
        }

        long[]
            vIds = new long[vIdList.size()];

        int
            n = -1;
        for(long wId : vIdList)
        {
            vIds[++n] = wId;
        }

        return vIds;
    }

    public static void setString(PreparedStatement iPreparedStatement, int iPosition, String iValue) throws SQLException
    {
        if (iValue == null)
        {
            iPreparedStatement.setNull(iPosition, Types.VARCHAR);
        }
        else
        {
            iPreparedStatement.setString(iPosition, iValue);
        }
    }

    public static void setLong(PreparedStatement iPreparedStatement, int iPosition, long iValue) throws SQLException
    {
        if (iValue < 0)
        {
            iPreparedStatement.setNull(iPosition, Types.INTEGER);
        }
        else
        {
            iPreparedStatement.setLong(iPosition, iValue);
        }
    }

    public static final long getLong(
        ResultSet iResultSet,
        String iColumnName
    )
        throws SQLException
    {
        Number
            vNumber = iResultSet.getBigDecimal(iColumnName);

        return vNumber == null ? -1 : vNumber.longValue();
    }

    public static final int getInt(
        ResultSet iResultSet,
        String iColumnName
    )
        throws SQLException
    {
        Number
            vNumber = iResultSet.getBigDecimal(iColumnName);

        return vNumber == null ? -1 : vNumber.intValue();
    }


    public static void setBoolean(PreparedStatement iPreparedStatement, int iPosition, Boolean iValue) throws SQLException
    {
        if (iValue == null)
        {
            iPreparedStatement.setNull(iPosition, Types.BOOLEAN);
        }
        else
        {
            iPreparedStatement.setBoolean(iPosition, iValue);
        }
    }

    public static void setInt(PreparedStatement iPreparedStatement, int iPosition, Integer iValue) throws SQLException
    {
        if (iValue == null || iValue < 0)
        {
            iPreparedStatement.setNull(iPosition, Types.INTEGER);
        }
        else
        {
            iPreparedStatement.setLong(iPosition, iValue);
        }
    }


    /**
     * @param iStatement
     * @param iIndex .
     * @param iFloat .
     * @throws java.sql.SQLException
     */
    public static final void setFloat(
        PreparedStatement iStatement,
        int iIndex,
        double iFloat
    )
        throws SQLException
    {
        if(Double.isNaN(iFloat))
        {
            iStatement.setNull(iIndex, Types.DECIMAL);
        }
        else
        {
            iStatement.setDouble(iIndex, iFloat);
        }

        return;
    }


    /**
     * @param iStatement
     * @param iIndex .
     * @param iDouble .
     * @throws java.sql.SQLException
     */
    public static final void setDouble(
        PreparedStatement iStatement,
        int iIndex,
        double iDouble
    )
        throws SQLException
    {
        if(Double.isNaN(iDouble))
        {
            iStatement.setNull(iIndex, Types.DECIMAL);
        }
        else
        {
            iStatement.setDouble(iIndex, iDouble);
        }

        return;
    }


    /**
     * @param iResultSet
     * @param iColumnName
     * @return iResultSet.getBigDecimal(iColumnName), Float.NaN if value is null
     * @throws java.sql.SQLException .
     */
    public static final float getFloat(
        ResultSet iResultSet,
        String iColumnName
    )
        throws SQLException
    {
        Number
            vNumber = iResultSet.getBigDecimal(iColumnName);

        return vNumber == null ? Float.NaN : vNumber.floatValue();
    }

    /**
     * @param iResultSet
     * @param iColumnIndex
     * @return iResultSet.getBigDecimal(iColumnName), Float.NaN if value is null
     * @throws java.sql.SQLException .
     */
    public static final float getFloat(
        ResultSet iResultSet,
        int iColumnIndex
    )
        throws SQLException
    {
        Number
            vNumber = iResultSet.getBigDecimal(iColumnIndex);

        return vNumber == null ? Float.NaN : vNumber.floatValue();
    }

    /**
     * @param iResultSet
     * @param iColumnName
     * @return iResultSet.getBigDecimal(iColumnName), Float.NaN if value is null
     * @throws java.sql.SQLException .
     */
    public static final double getDouble(
        ResultSet iResultSet,
        String iColumnName
    )
        throws SQLException
    {
        Number
            vNumber = iResultSet.getBigDecimal(iColumnName);

        return vNumber == null ? Double.NaN : vNumber.doubleValue();
    }

    /**
     * @param iResultSet
     * @param iColumnIndex
     * @return iResultSet.getBigDecimal(iColumnName), Float.NaN if value is null
     * @throws java.sql.SQLException .
     */
    public static final double getDouble(
        ResultSet iResultSet,
        int iColumnIndex
    )
        throws SQLException
    {
        Number
            vNumber = iResultSet.getBigDecimal(iColumnIndex);

        return vNumber == null ? Double.NaN : vNumber.doubleValue();
    }

    public static final Timestamp getTimestamp(ResultSet iResultSet, String iColumnName)  throws SQLException
    {
        long
            vTime = iResultSet.getLong(iColumnName);

        if(vTime <= 0)
        {
            return null;
        }

        return iResultSet.getTimestamp(iColumnName);
    }

    public static final void setTimestamp(
        PreparedStatement iStatement,
        int iIndex,
        Timestamp iTimestamp
    )
        throws SQLException
    {
        if(iTimestamp == null)
        {
            iStatement.setLong(iIndex, DBConstants.DB_DEFAULT_DATE_LONG);
        }
        else
        {
            iStatement.setTimestamp(iIndex, iTimestamp);
        }

        return;
    }

    /**
     * @param iStatement
     * @param iIndex .
     * @param iDate .
     * @throws java.sql.SQLException
     */
    public static final void setDate(
        PreparedStatement iStatement,
        int iIndex,
        java.util.Date iDate
    )
        throws SQLException
    {
        if(iDate == null)
        {
            iStatement.setNull(iIndex, Types.DATE);
        }
        else
        {
            iStatement.setDate(iIndex, new Date(iDate.getTime()));
        }

        return;
    }

}

