package com.cofii2.mysql.interfaces;

import com.cofii2.mysql.enums.QueryResult;

@FunctionalInterface
public interface IQueryResult {

    public void queryAction(QueryResult qResult);
}
