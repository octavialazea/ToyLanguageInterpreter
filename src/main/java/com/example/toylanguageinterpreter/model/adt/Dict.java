package com.example.toylanguageinterpreter.model.adt;

import java.util.HashMap;
import java.util.Map;

public class Dict<T1, T2> implements IDict<T1, T2> {
    HashMap<T1, T2> dictionary;

    public Dict() {
        dictionary = new HashMap<>();
    }
    public Dict(HashMap<T1,T2> d){
        dictionary = d;
    }

    @Override
    public void add(T1 v1, T2 v2) {
        dictionary.putIfAbsent(v1, v2);
    }

    @Override
    public void update(T1 v1, T2 v2) {
        dictionary.replace(v1, v2);
    }

    @Override
    public T2 lookup(T1 id) {
        if (dictionary.containsKey(id))
            return dictionary.get(id);
        return null;
    }

    @Override
    public boolean isDefined(T1 id) {
        return dictionary.containsKey(id);
    }

    @Override
    public String toString() {
        return String.valueOf(dictionary);
    }

    @Override
    public HashMap<T1,T2> getContent() {
        return dictionary;
    }

    @Override
    public void setContent(HashMap<T1,T2> d){ dictionary = d;}

    @Override
    public Dict<T1, T2> deepCopy() {
        HashMap<T1,T2> copyDict = new HashMap<>();
        for (Map.Entry<T1,T2> element : dictionary.entrySet()){
            T1 key = element.getKey();
            T2 value = element.getValue();
            copyDict.put(key,value);
        }
        return new Dict<>(copyDict);
    }

}
