package com.cofii2.mysql.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface IResultAndException {
    public void action(ResultSet rs, boolean rsValues ,SQLException ex) throws SQLException;
}
