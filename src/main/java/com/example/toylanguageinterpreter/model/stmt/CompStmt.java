package com.example.toylanguageinterpreter.model.stmt;


import com.example.toylanguageinterpreter.PrgState;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.exceptions.StatementExecException;
import com.example.toylanguageinterpreter.model.adt.IDict;
import com.example.toylanguageinterpreter.model.adt.IStack;
import com.example.toylanguageinterpreter.model.types.Type;

public class CompStmt implements IStmt {
    private IStmt first, second;

    public CompStmt(IStmt f, IStmt s){
        first = f;
        second = s;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementExecException {
        IStack<IStmt> stk = state.getExeStack();
        stk.push(second);
        stk.push(first);
        //state.setExeStack(stk);
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv));
    }

    public String toString(){ return " " + first.toString() + ";" + second.toString();}
}
