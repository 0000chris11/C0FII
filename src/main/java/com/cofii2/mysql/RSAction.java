package com.cofii2.mysql;

import java.sql.ResultSet;

@FunctionalInterface
public interface RSAction {
    public void actionRS(ResultSet rs);
}
