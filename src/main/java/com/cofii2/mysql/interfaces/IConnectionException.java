package com.cofii2.mysql.interfaces;

import java.sql.SQLException;

public interface IConnectionException {
    
    public void exception(SQLException ex);

    public void succes();
}
