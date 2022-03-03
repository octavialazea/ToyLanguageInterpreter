package com.example.toylanguageinterpreter.model.types;


import com.example.toylanguageinterpreter.model.value.IntValue;
import com.example.toylanguageinterpreter.model.value.Value;

public class IntType implements Type {

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof IntType;
    }

    public Type deepCopy() {
        return new IntType();
    }

    @Override
    public String toString(){
        return "int";
    }
}
