package com.example.toylanguageinterpreter.model.stmt;


import com.example.toylanguageinterpreter.PrgState;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.model.adt.IDict;
import com.example.toylanguageinterpreter.model.adt.IHeap;
import com.example.toylanguageinterpreter.model.adt.IList;
import com.example.toylanguageinterpreter.model.exp.Exp;
import com.example.toylanguageinterpreter.model.types.Type;
import com.example.toylanguageinterpreter.model.value.Value;

public class PrintStmt implements IStmt{

    private Exp expression;

    public PrintStmt(Exp exp){
        this.expression = exp;
    }

    @Override
    public PrgState execute(PrgState state){
        IList<Value> output = state.getOutList();
        IDict<String, Value> symTable = state.getSymTable();
        IHeap<Value> heap = state.getHeap();
        output.add(expression.eval(symTable, heap));
        state.setOutList(output);
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws MyException {
        expression.typecheck(typeEnv);
        return typeEnv;
    }

    public String toString(){ return " Print(" + expression.toString() + ")";}
}
