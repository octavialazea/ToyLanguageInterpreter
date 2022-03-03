//package com.example.toylanguageinterpreter.model.stmt;
//
//import com.example.toylanguageinterpreter.PrgState;
//import com.example.toylanguageinterpreter.exceptions.MyException;
//import com.example.toylanguageinterpreter.model.adt.IDict;
//import com.example.toylanguageinterpreter.model.adt.ILockTable;
//import com.example.toylanguageinterpreter.model.types.Type;
//import com.example.toylanguageinterpreter.model.value.IntValue;
//import com.example.toylanguageinterpreter.model.value.Value;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//
//public class UnlockStmt implements IStmt {
//
//    private String var;
//
//    public UnlockStmt(String v){
//        var = v;
//    }
//
//    @Override
//    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
//        IDict<String, Value> symTable = state.getSymTable();
//        ILockTable<Integer, Integer> lockTable = state.getLockTable();
//        if (!symTable.isDefined(var))
//            throw new MyException("Var not defined in the symbol table");
//        Value v = symTable.lookup(var);
//        IntValue intValue = (IntValue) v;
//        synchronized (lockTable)
//        {
//            if (!lockTable.contains(intValue.getValue()))
//                throw new MyException("Value not in the lock table");
//            else if (lockTable.get(intValue.getValue()) == state.getId())
//                lockTable.add(intValue.getValue(), -1);
//        }
//
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
//        return "Unlock(" + var + ")";
//    }
//}
