//package com.example.toylanguageinterpreter.model.stmt;
//
//import com.example.toylanguageinterpreter.PrgState;
//import com.example.toylanguageinterpreter.exceptions.MyException;
//import com.example.toylanguageinterpreter.model.adt.IDict;
//import com.example.toylanguageinterpreter.model.adt.ILockTable;
//import com.example.toylanguageinterpreter.model.stmt.IStmt;
//import com.example.toylanguageinterpreter.model.types.Type;
//import com.example.toylanguageinterpreter.model.value.IntValue;
//import com.example.toylanguageinterpreter.model.value.Value;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//
//public class NewLockStmt implements IStmt {
//
//    private String var;
//    private int nextFree = 0;
//
//    public NewLockStmt(String v){
//        var = v;
//    }
//
//    @Override
//    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
//        ILockTable<Integer, Integer> lockTable = state.getLockTable();
//        IDict<String, Value> symTable = state.getSymTable();
//        synchronized (lockTable){
//            lockTable.add(nextFree, -1);
//        }
//        IntValue intValue = new IntValue(nextFree);
//        if (symTable.isDefined(var))
//            symTable.update(var, intValue);
//        else symTable.add(var, intValue);
//        nextFree++;
//        return null;
//    }
//
//    @Override
//    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws MyException {
//        return null;
//    }
//
//    @Override
//    public String toString(){
//        return "NewLock(" + var + ")";
//    }
//}