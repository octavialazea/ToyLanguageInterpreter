package com.example.toylanguageinterpreter.model.adt;

import java.util.HashMap;

public class LatchTable<T1,T2> implements ILatchTable<T1,T2>{

    HashMap<T1,T2> latchTable;

    public LatchTable(){
        latchTable = new HashMap<>();
    }

    @Override
    public void add(T1 v1, T2 v2) {
        latchTable.putIfAbsent(v1,v2);
    }

    @Override
    public void update(T1 v1, T2 v2) {
        latchTable.replace(v1,v2);
    }

    @Override
    public T2 lookup(T1 id) {
        if (isDefined(id))
            return latchTable.get(id);
        return null;
    }

    @Override
    public boolean isDefined(T1 id) {
        return latchTable.containsKey(id);
    }

    @Override
    public HashMap<T1, T2> getContent() {
        return latchTable;
    }
}
