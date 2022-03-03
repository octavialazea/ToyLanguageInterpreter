package com.example.toylanguageinterpreter.model.adt;

import java.util.HashMap;

public interface IBarrierTable<T1,T2> {
    void add(T1 v1, T2 v2);
    void update(T1 v1, T2 v2);
    T2 lookup(T1 id);
    boolean isDefined(T1 id);
    String toString();
    HashMap<T1,T2> getContent();
    Dict<T1,T2> deepCopy();
    int getNextFree();
}
