package ru.gt2.gar.db.ps;

import com.google.common.collect.ImmutableList;

import java.lang.reflect.Method;

public record InsertData(String insertSQL, ImmutableList<Method> accessors) {
}
