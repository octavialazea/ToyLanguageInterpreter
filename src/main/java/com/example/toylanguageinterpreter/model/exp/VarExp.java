package com.example.toylanguageinterpreter.model.exp;

import com.example.toylanguageinterpreter.exceptions.ExpressionEvalException;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.model.adt.IDict;
import com.example.toylanguageinterpreter.model.adt.IHeap;
import com.example.toylanguageinterpreter.model.types.Type;
import com.example.toylanguageinterpreter.model.value.Value;

public class VarExp implements Exp{
    String id;

    public VarExp(String i){ id = i;}

    public Value eval(IDict<String, Value> symTable, IHeap<Value> heap) throws ExpressionEvalException
    {
        return symTable.lookup(id);
    }

    public String toString() { return id;}

    @Override
    public Exp deepCopy() {
        return new VarExp(id);
    }

    @Override
    public Type typecheck(IDict<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }

}
