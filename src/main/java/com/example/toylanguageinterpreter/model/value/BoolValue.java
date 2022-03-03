package com.example.toylanguageinterpreter.model.value;

import com.example.toylanguageinterpreter.model.types.BoolType;
import com.example.toylanguageinterpreter.model.types.Type;

public class BoolValue implements Value {

    boolean value;

    public BoolValue(){
        this.value = false;
    }

    public BoolValue(boolean i){
        this.value = i;
    }

    public boolean getValue(){
        return value;
    }

    @Override
    public String toString(){
        return Boolean.toString(value);
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(this.value);
    }

    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass())
            return false;
        BoolValue o_value = (BoolValue) o;
        return o_value.value == this.value;
    }
}
