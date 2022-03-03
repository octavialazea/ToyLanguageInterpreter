package com.example.toylanguageinterpreter.model.adt;

import java.util.ArrayList;

public interface IList<T> {
    void add(T v);
    T getFirst();
    String toString();
    boolean empty();
    void clear();
    ArrayList<T> getList();
}
