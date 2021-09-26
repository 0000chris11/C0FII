package com.cofii2.mysql.enums;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryResult {
    
    public static final int VALUES = 1;
    public static final int EXCEPTION = 2;

    private int value;
    private ResultSet resultSet;
    private SQLException sqlException;
    //-----------------------------------------------
    /*
    QueryResult(int value){
        this.value = value;
    }
    QueryResult(int value, ResultSet resultSet){
        this.value = value;
        this.resultSet = resultSet;
    }
    QueryResult(int value, SQLException sqlException){
        this.value = value;
        this.sqlException = sqlException;
    }
    */
    //-----------------------------------------------
    public ResultSet getResultSet() {
        return resultSet;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }
    public SQLException getSqlException() {
        return sqlException;
    }
    public void setSqlException(SQLException sqlException) {
        this.sqlException = sqlException;
    }
    
}
