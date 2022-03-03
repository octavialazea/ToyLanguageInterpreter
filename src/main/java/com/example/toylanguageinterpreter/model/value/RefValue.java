package com.example.toylanguageinterpreter.model.value;

import com.example.toylanguageinterpreter.model.types.RefType;
import com.example.toylanguageinterpreter.model.types.Type;

public class RefValue implements Value{

    private int address;
    private Type locationType;

    public RefValue(int a, Type loc) {
        address = a;
        locationType = loc;
    }
    public int getAddress(){ return address;}

    public Type getLocationType(){return locationType;}

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Value deepCopy() {
        return new RefValue(address, locationType);
    }

    @Override
    public String toString(){
        return "(" + String.valueOf(address) + ","  +  locationType.toString() + ")";
    }

    public void setAddress(int a){
        address = a;
    }
}
