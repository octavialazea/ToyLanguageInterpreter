package com.example.toylanguageinterpreter.model.adt;

import java.util.Map;

public interface IFileTable<T1, T2> {
    void add(T1 v1, T2 v2);
    void update(T1 v1, T2 v2);
    T2 lookup(T1 id);
    boolean isDefined(T1 id);
    String toString();
    void remove(T1 v1);
    Map<T1,T2> getTable();
    T1 getFirst();
}
