package com.example.toylanguageinterpreter.model.value;





import com.example.toylanguageinterpreter.model.types.StringType;
import com.example.toylanguageinterpreter.model.types.Type;

import java.util.Objects;

public class StringValue implements Value{

    private final String value;

    public StringValue(){
        this.value = "";
    }

    public StringValue(String s){
        value = s;
    }

    @Override
    public Type getType() { return new StringType();}

    public String getValue() { return value;}

    @Override
    public Value deepCopy() {
        return new StringValue(value);
    }

    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass())
            return false;
        StringValue o_value = (StringValue) o;
        return Objects.equals(o_value.value, this.value);
    }

    @Override
    public String toString(){
        return "\"" + value + "\"";
    }
}
