package com.example.toylanguageinterpreter.model.value;


import com.example.toylanguageinterpreter.model.types.IntType;
import com.example.toylanguageinterpreter.model.types.Type;

public class IntValue implements Value {

    private int value;

    public IntValue() {
        this.value = 0;
    }

    public IntValue(int v) {
        value = v;
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public Value deepCopy() {
        return new IntValue(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass())
            return false;
        IntValue o_value = (IntValue) o;
        return o_value.value == this.value;
    }

}
