package com.cofii2.mysql;

import java.sql.SQLException;

@FunctionalInterface
public interface ExceptionAction {
    public void exceptionAction(SQLException ex);
}
