package com.example.toylanguageinterpreter.model.exp;

import com.example.toylanguageinterpreter.exceptions.ExpressionEvalException;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.model.adt.IDict;
import com.example.toylanguageinterpreter.model.adt.IHeap;
import com.example.toylanguageinterpreter.model.types.Type;
import com.example.toylanguageinterpreter.model.value.Value;

public class ValueExp implements Exp{
    Value value;

    public ValueExp(Value v) {
        value = v;
    }

    @Override
    public Value eval(IDict<String, Value> symTable, IHeap<Value> heap) throws ExpressionEvalException {
        return value;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(value);
    }

    @Override
    public Type typecheck(IDict<String, Type> typeEnv) throws MyException {
        return value.getType();
    }

    public String toString(){ return value.toString();}
}
