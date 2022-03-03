package com.example.toylanguageinterpreter.model.types;


import com.example.toylanguageinterpreter.model.value.RefValue;
import com.example.toylanguageinterpreter.model.value.Value;

public class RefType implements Type{
    private Type inner;

    public RefType(Type in){
        this.inner = in;
    }

    public Type getInner() { return inner;}

    @Override
    public boolean equals(Object o){
        if (o instanceof RefType)
            return inner.equals(((RefType) o).getInner());
        else return false;
    }

    @Override
    public String toString(){ return "Ref(" + inner.toString() + ")";}

    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }
}
