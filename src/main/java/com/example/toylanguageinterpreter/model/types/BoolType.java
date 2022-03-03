package com.example.toylanguageinterpreter.model.types;


import com.example.toylanguageinterpreter.model.value.BoolValue;
import com.example.toylanguageinterpreter.model.value.Value;

public class BoolType implements Type {

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BoolType)
            return true;
        else return false;
    }


    public Type deepCopy() {
        return new BoolType();
    }

    @Override
    public String toString(){
        return "bool";
    }
}
