//package com.example.toylanguageinterpreter.model.stmt;
//
//import com.example.toylanguageinterpreter.PrgState;
//import com.example.toylanguageinterpreter.exceptions.MyException;
//import com.example.toylanguageinterpreter.model.adt.IDict;
//import com.example.toylanguageinterpreter.model.adt.IHeap;
//import com.example.toylanguageinterpreter.model.adt.ISemaphoreTable;
//import com.example.toylanguageinterpreter.model.adt.SemaphoreTable;
//import com.example.toylanguageinterpreter.model.exp.Exp;
//import com.example.toylanguageinterpreter.model.types.IntType;
//import com.example.toylanguageinterpreter.model.types.Type;
//import com.example.toylanguageinterpreter.model.value.IntValue;
//import com.example.toylanguageinterpreter.model.value.Value;
//import javafx.util.Pair;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
//public class NewSemaphoreStmt implements IStmt {
//
//    private String var;
//    private Exp exp;
//    private static Lock lock = new ReentrantLock();
//
//
//    public NewSemaphoreStmt(String v, Exp e){
//        var = v;
//        exp = e;
//    }
//    @Override
//    public PrgState execute(PrgState state) throws MyException, IOException {
//
////        ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreTable();
////        synchronized (semaphoreTable){
////            IHeap<Value> heap = state.getHeap();
////            IDict<String, Value> symTable = state.getSymTable();
////            Value number = exp.eval(symTable, heap);
////            IntValue intNumber = (IntValue) number;
////            IntValue intValue = new IntValue(nextFree);
////            semaphoreTable.add(nextFree, new Pair<>(intNumber.getValue(), new ArrayList<>()));
////            nextFree++;
////            if (symTable.isDefined(var))
////                symTable.update(var, intValue);
////            else
////                symTable.add(var, intValue);
////            state.setSymTable(symTable);
////            state.setSemaphoreTable(semaphoreTable);
////        }
//        lock.lock();
//        IDict<String, Value> symTable = state.getSymTable();
//        IHeap<Value> heap = state.getHeap();
//        ISemaphoreTable semaphoreTable = state.getSemaphoreTable();
//        try{
//            IntValue nr1 = (IntValue) (exp.eval(symTable,heap));
//            int n1 = nr1.getValue();
//            int nextFree = semaphoreTable.getFreeLocation();
//            semaphoreTable.add(nextFree,new Pair<>(n1, new ArrayList<>()));
//            if (symTable.isDefined(var) && symTable.lookup(var).getType().equals(new IntType()))
//                symTable.update(var, new IntValue(nextFree));
//
//        }
//        catch (RuntimeException e){
//            throw new MyException(e.getMessage());
//        }
//        lock.unlock();
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
//        return "NewSemaphore(" + var + ")";
//    }
//}
