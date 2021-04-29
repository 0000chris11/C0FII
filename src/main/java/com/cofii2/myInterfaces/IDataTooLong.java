package com.cofii2.myInterfaces;

import java.sql.SQLException;

@FunctionalInterface
public interface IDataTooLong {
    
    public void exception(SQLException ex);
}
