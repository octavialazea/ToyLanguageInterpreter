package com.example.toylanguageinterpreter.model.adt;

import java.util.HashMap;

public class BarrierTable<T1,T2> implements IBarrierTable<T1,T2>{

    HashMap<T1,T2> barrierTable;
    private int nextFree = 0;

    public BarrierTable(){
        barrierTable = new HashMap<>();
    }
    @Override
    public void add(T1 v1, T2 v2) {
        barrierTable.put(v1,v2);
    }

    @Override
    public void update(T1 v1, T2 v2) {
        barrierTable.replace(v1,v2);
    }

    @Override
    public T2 lookup(T1 id) {
        if (barrierTable.containsKey(id))
            return barrierTable.get(id);
        return null;
    }

    @Override
    public boolean isDefined(T1 id) {
        return barrierTable.containsKey(id);
    }

    @Override
    public HashMap getContent() {
        return barrierTable;
    }

    @Override
    public Dict deepCopy() {
        return null;
    }

    @Override
    public int getNextFree() {
        return ++nextFree;
    }
}
