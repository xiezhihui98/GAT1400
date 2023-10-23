package com.cz.viid.framework.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.logging.log4j.util.Strings;

import java.sql.*;

@MappedJdbcTypes(JdbcType.ARRAY)
public class StringToArrayTypeHandler extends BaseTypeHandler<String> {
    private static final String delimiter = ",";

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String objects, JdbcType jdbcType) throws SQLException {
        Object[] paramArray = objects != null ? objects.split(delimiter) : new Object[0];
        Array array = preparedStatement.getConnection().createArrayOf("varchar", paramArray);
        preparedStatement.setArray(i, array);
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return getList(resultSet.getArray(s));
    }

    @Override
    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return getList(resultSet.getArray(i));
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return getList(callableStatement.getArray(i));
    }

    public String getList(Array array) {
        if (array == null)
            return Strings.EMPTY;
        try {
            Object data = array.getArray();
            if (data != null) {
                String[] dataArray = (String[]) data;
                return String.join(delimiter, dataArray);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Strings.EMPTY;
    }
}
