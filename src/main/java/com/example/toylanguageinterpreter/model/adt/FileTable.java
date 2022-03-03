package com.example.toylanguageinterpreter.model.adt;

import java.util.HashMap;
import java.util.Map;

public class FileTable<T1, T2> implements IFileTable<T1, T2>{

    Map<T1, T2> fileTable;

    public FileTable() {
        fileTable = new HashMap<>();
    }


    @Override
    public void add(T1 v1, T2 v2) {
        fileTable.putIfAbsent(v1, v2);
    }

    @Override
    public void update(T1 v1, T2 v2) {
        fileTable.replace(v1, v2);
    }

    @Override
    public T2 lookup(T1 id) {
        if (fileTable.containsKey(id))
            return fileTable.get(id);
        return null;
    }

    @Override
    public boolean isDefined(T1 id) {
        return fileTable.containsKey(id);
    }

    @Override
    public String toString(){ return String.valueOf(fileTable); }

    @Override
    public void remove(T1 v1) {
        fileTable.remove(v1);
    }

    @Override
    public Map<T1,T2> getTable() {
        return fileTable;
    }

    @Override
    public T1 getFirst() {
        return null;
    }
}
