package com.example.toylanguageinterpreter.model.adt;

import java.util.HashMap;
import java.util.Map;

public class SemaphoreTable<T1,T2> implements ISemaphoreTable<T1,T2> {

    HashMap<T1, T2> semaphore;
    static int freeLocation=0;

    public SemaphoreTable(){
        semaphore = new HashMap<>();
    }

    @Override
    public void add(T1 v1, T2 v2) {
        semaphore.put(v1,v2);
    }

    @Override
    public void update(T1 v1, T2 v2) {
        semaphore.replace(v1,v2);
    }

    @Override
    public T2 lookup(T1 id) {
        if (semaphore.containsKey(id))
            return semaphore.get(id);
        return null;
    }

    @Override
    public boolean isDefined(T1 id) {
        return semaphore.containsKey(id);
    }

    @Override
    public HashMap<T1,T2> getContent() {
        return semaphore;
    }

    @Override
    public T2 get(T1 key) {
        return semaphore.get(key);
    }

    @Override
    public synchronized int getFreeLocation(){
        ++freeLocation;
        return freeLocation;
    }
}
