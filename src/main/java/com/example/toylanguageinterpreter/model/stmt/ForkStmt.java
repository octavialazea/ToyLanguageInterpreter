package com.example.toylanguageinterpreter.model.stmt;

import com.example.toylanguageinterpreter.PrgState;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.model.adt.ExecutionStack;
import com.example.toylanguageinterpreter.model.adt.IDict;
import com.example.toylanguageinterpreter.model.adt.IStack;
import com.example.toylanguageinterpreter.model.types.Type;
import com.example.toylanguageinterpreter.model.value.Value;

import java.io.IOException;

public class ForkStmt implements IStmt {

    private IStmt stmt;

    public ForkStmt(IStmt s) {
        stmt = s;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        IStack<IStmt> newStack = new ExecutionStack<>();
        IDict<String, Value> newSymTable = state.getSymTable().deepCopy();
        return new PrgState(newStack, newSymTable, state.getOutList(), state.getFileTable(), state.getHeap(), state.getBarrierTable(),stmt);
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws MyException {
        stmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    public IStmt getStmt() {
        return stmt;
    }

    @Override
    public String toString() {
        return "fork(" + stmt.toString() + ")";
    }
}