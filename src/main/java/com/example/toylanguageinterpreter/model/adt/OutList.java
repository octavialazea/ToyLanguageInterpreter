package com.example.toylanguageinterpreter.model.adt;

import java.util.ArrayList;

public class OutList<T> implements IList<T> {
    private ArrayList<T> outList;

    public OutList(){
        outList = new ArrayList<>();
    }

    @Override
    public void add(T v) {
        outList.add(v);
    }

    public T getFirst() {return outList.get(0);}

    @Override
    public boolean empty() {
        return outList.isEmpty();
    }

    @Override
    public void clear(){
        outList.clear();
    }

    @Override
    public String toString(){
        return String.valueOf(outList);
    }

    @Override
    public ArrayList<T> getList(){
        return outList;
    }

}
