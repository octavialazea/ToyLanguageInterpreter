package com.example.toylanguageinterpreter.model.exp;

import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.model.adt.IDict;
import com.example.toylanguageinterpreter.model.adt.IHeap;
import com.example.toylanguageinterpreter.model.types.Type;
import com.example.toylanguageinterpreter.model.value.Value;

public interface Exp {

    Value eval(IDict<String, Value> symTable, IHeap<Value> heap) throws MyException;
    String toString();
    Exp deepCopy();
    Type typecheck(IDict<String, Type> typeEnv) throws MyException;
}
