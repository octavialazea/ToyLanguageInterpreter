package com.example.toylanguageinterpreter.model.stmt;

import com.example.toylanguageinterpreter.PrgState;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.exceptions.StatementExecException;
import com.example.toylanguageinterpreter.model.adt.IBarrierTable;
import com.example.toylanguageinterpreter.model.adt.IDict;
import com.example.toylanguageinterpreter.model.types.IntType;
import com.example.toylanguageinterpreter.model.types.Type;
import com.example.toylanguageinterpreter.model.value.IntValue;
import com.example.toylanguageinterpreter.model.value.Value;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class AwaitStmt implements IStmt{

    private String var;

    public AwaitStmt(String v){
        var = v;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        IDict<String, Value> symTable = state.getSymTable();
        IBarrierTable<Integer, Pair<Integer, List<Integer>>> barrierTable = state.getBarrierTable();
        Value val = symTable.lookup(var);
        IntValue foundIndex = (IntValue) val;
        synchronized (barrierTable) {
            if (val.getType().equals(new IntType())) {
                if (barrierTable.isDefined(foundIndex.getValue())){
                    Pair<Integer, List<Integer>> p = barrierTable.lookup(foundIndex.getValue());
                    if (p.getKey() > p.getValue().size()){
                        if (p.getValue().contains(state.getId()))
                            state.getExeStack().push(this);
                        else {
                            p.getValue().add(state.getId());
                            state.getExeStack().push(this);
                        }
                    }
                }else
                    throw new MyException("Index not in the barrier table");
            } else throw new MyException("Val not of int type");
        }
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws MyException {
        if (!typeEnv.lookup(var).equals(new IntType()))
            throw new StatementExecException("Var does not have int type");
        return null;
    }

    @Override
    public String toString(){
        return "Await(" + var + ")";
    }
}
