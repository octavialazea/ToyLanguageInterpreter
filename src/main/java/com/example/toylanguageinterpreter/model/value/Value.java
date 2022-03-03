package com.example.toylanguageinterpreter.model.value;


import com.example.toylanguageinterpreter.model.types.Type;

public interface Value {
    Type getType();
    Value deepCopy();
}
