package com.cofii2.myInterfaces;

import java.sql.SQLException;

public interface ISQL {
    
    public void exception(SQLException ex, String query);
}
