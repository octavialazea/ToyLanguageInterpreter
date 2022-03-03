package com.example.toylanguageinterpreter.model.types;


import com.example.toylanguageinterpreter.model.value.StringValue;
import com.example.toylanguageinterpreter.model.value.Value;

public class StringType implements Type{



    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public boolean equals(Object o){
        return o instanceof StringType;
    }

    public Type deepCopy(){ return new StringType();}

    @Override
    public String toString(){ return "string";}
}
