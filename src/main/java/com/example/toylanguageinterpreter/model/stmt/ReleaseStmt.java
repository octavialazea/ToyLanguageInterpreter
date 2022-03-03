//package com.example.toylanguageinterpreter.model.stmt;
//
//import com.example.toylanguageinterpreter.PrgState;
//import com.example.toylanguageinterpreter.exceptions.MyException;
//import com.example.toylanguageinterpreter.model.adt.IDict;
//import com.example.toylanguageinterpreter.model.adt.ISemaphoreTable;
//import com.example.toylanguageinterpreter.model.types.IntType;
//import com.example.toylanguageinterpreter.model.types.Type;
//import com.example.toylanguageinterpreter.model.value.IntValue;
//import com.example.toylanguageinterpreter.model.value.Value;
//import javafx.util.Pair;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.List;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
//public class ReleaseStmt implements IStmt {
//
//    private String var;
//    private static Lock lock = new ReentrantLock();
//
//    public ReleaseStmt(String v){
//        var = v;
//    }
//
//    @Override
//    public PrgState execute(PrgState state) throws MyException, IOException {
//        lock.lock();
////        IDict<String, Value> symTable = state.getSymTable();
////        ISemaphoreTable<Integer,Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreTable();
////        if (symTable.isDefined(var)) {
////            Value foundIndex = symTable.lookup(var);
////            if (foundIndex.getType() instanceof IntType){
////                IntValue intIndex = (IntValue) foundIndex;
////
////                    if (!semaphoreTable.isDefined(intIndex.getValue()))
////                        throw new MyException("Index not in the semaphore table");
////                    Pair<Integer, List<Integer>> myPair = semaphoreTable.get(intIndex.getValue());
////                    Integer N1 = myPair.getKey();
////                    List<Integer> list1 = myPair.getValue();
////                    if (list1.contains(state.getId()))
////                        list1.remove(state.getId());
////
////            }else throw new MyException("Value not of IntType");
////
////
////        }else throw new MyException("Var not defined in the symbol table");
//        try{
//            ISemaphoreTable<Integer, Pair<Integer,List<Integer>>> semaphoreTable = state.getSemaphoreTable();
//            IntValue foundIndex = (IntValue) state.getSymTable().lookup(var);
//            if (foundIndex == null)
//                throw new Exception("Var not in symbol table");
//            Pair<Integer,List<Integer>> semValue = semaphoreTable.get(foundIndex.getValue());
//            List<Integer> threads = semValue.getValue();
//            Integer nrMax = semValue.getKey();
//            if (threads.contains(state.getId()))
//                threads.remove(state.getId());
//            state.getSemaphoreTable().add(foundIndex.getValue(), new Pair<>(nrMax, threads));
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }finally {
//            lock.unlock();
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
//        return "release(" + var + ")";
//    }
//}
