package com.example.toylanguageinterpreter.model.stmt;


import com.example.toylanguageinterpreter.PrgState;
import com.example.toylanguageinterpreter.exceptions.ExpressionEvalException;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.model.adt.IDict;
import com.example.toylanguageinterpreter.model.types.Type;
import com.example.toylanguageinterpreter.model.value.Value;

public class VarDeclStmt implements IStmt{
    private final String name;
    private final Type type;

    public VarDeclStmt(String n, Type t){
        name = n;
        type = t;
    }


    @Override
    public PrgState execute(PrgState state) throws ExpressionEvalException {
        IDict<String, Value> symTable = state.getSymTable();
        if (symTable.isDefined(name) || name == null)
            throw new ExpressionEvalException("Invalid or existing key!");
        symTable.add(name, type.defaultValue());
        state.setSymTable(symTable);
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws MyException {
        typeEnv.add(name,type);
        return typeEnv;
    }

    public String toString(){ return ""+type.toString() +  " " + name; }
}
