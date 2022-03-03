package com.example.toylanguageinterpreter.model.adt;

import java.util.HashMap;

public class HeapTable<T> implements IHeap<T> {
    private HashMap<Integer, T> heap;
    private int freeLocation;

    public HeapTable() {
        heap = new HashMap<>();
        freeLocation = 1;
    }


    @Override
    public String toString() {
        return String.valueOf(heap);
    }

    @Override
    public synchronized void add(T v) {
        heap.put(freeLocation++, v);
    }

    @Override
    public synchronized  void update(int key, T value) {
        heap.put(key, value);
    }

    @Override
    public T lookup(Integer address) {
        return heap.get(address);
    }

    @Override
    public HashMap<Integer, T> getContent() {
        return heap;
    }

    @Override
    public T getValue(int key) {
        return heap.get(key);
    }

    @Override
    public void remove(int key) {
        heap.remove(key);
    }

    @Override
    public void setContent(HashMap<Integer, T> newContent) {
        heap = newContent;
    }

    @Override
    public int getFreeLocation(){return freeLocation;}

    @Override
    public boolean isDefined(int id) {
        return heap.containsKey(id);
    }
}
