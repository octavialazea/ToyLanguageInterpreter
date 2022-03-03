package com.example.toylanguageinterpreter.model.stmt;

import com.example.toylanguageinterpreter.PrgState;
import com.example.toylanguageinterpreter.exceptions.ExpressionEvalException;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.exceptions.StatementExecException;
import com.example.toylanguageinterpreter.model.adt.IBarrierTable;
import com.example.toylanguageinterpreter.model.adt.IDict;
import com.example.toylanguageinterpreter.model.adt.IHeap;
import com.example.toylanguageinterpreter.model.exp.Exp;
import com.example.toylanguageinterpreter.model.types.BoolType;
import com.example.toylanguageinterpreter.model.types.IntType;
import com.example.toylanguageinterpreter.model.types.Type;
import com.example.toylanguageinterpreter.model.value.IntValue;
import com.example.toylanguageinterpreter.model.value.Value;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewBarrierStmt implements IStmt{

    private String var;
    private Exp exp;
    private Lock lock = new ReentrantLock();

    public NewBarrierStmt(String v, Exp e){
        var = v;
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        IDict<String, Value> symTable = state.getSymTable();
        IHeap<Value> heap = state.getHeap();
        IBarrierTable<Integer, Pair<Integer, List<Integer>>> barrierTable = state.getBarrierTable();
        synchronized (barrierTable){
        Value expEval = exp.eval(symTable,heap);
        if (expEval.getType().equals(new IntType())){
            IntValue nr = (IntValue) expEval;
            int nextFree = barrierTable.getNextFree();
            barrierTable.add(nextFree, new Pair<>(nr.getValue(), new ArrayList<>()));
            if (symTable.isDefined(var)  && symTable.lookup(var).getType().equals(new IntType()))
                symTable.update(var, new IntValue(nextFree));
            else throw new MyException("Var not defined in the symbol table or not an int type");

        }
        else throw new ExpressionEvalException("Exp not of int type");
        }
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (!(typexp.equals(new IntType()) && typeEnv.lookup(var).equals(new IntType())))
            throw new StatementExecException("Invalid types!");
        return null;
    }

    @Override
    public String toString(){
        return "NewBarrier(" + var + "," + exp + ")";
    }
}
