package com.example.toylanguageinterpreter.model.adt;

import java.util.HashMap;

public interface IHeap<T> {

    void add(T v);
    void update(int key, T value);
    T lookup(Integer address);
    HashMap<Integer, T> getContent();
    T getValue(int key);
    void remove(int key);
    void setContent(HashMap<Integer, T> newContent);
    int getFreeLocation();
    boolean isDefined(int id);

}
