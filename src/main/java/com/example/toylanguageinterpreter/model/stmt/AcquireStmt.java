//package com.example.toylanguageinterpreter.model.stmt;
//
//import com.example.toylanguageinterpreter.PrgState;
//import com.example.toylanguageinterpreter.exceptions.MyException;
//import com.example.toylanguageinterpreter.model.adt.IDict;
//import com.example.toylanguageinterpreter.model.adt.ISemaphoreTable;
//import com.example.toylanguageinterpreter.model.adt.IStack;
//import com.example.toylanguageinterpreter.model.types.IntType;
//import com.example.toylanguageinterpreter.model.types.Type;
//import com.example.toylanguageinterpreter.model.value.IntValue;
//import com.example.toylanguageinterpreter.model.value.Value;
//import javafx.scene.control.Alert;
//import javafx.scene.control.ButtonType;
//import javafx.util.Pair;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.List;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
//public class AcquireStmt implements IStmt {
//
//    private String var;
//    private static Lock lock = new ReentrantLock();
//
//    public AcquireStmt(String v) {
//        var = v;
//    }
//
//    @Override
//    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
////        lock.lock();
////        IDict<String, Value> symTable = state.getSymTable();
////        ISemaphoreTable<Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreTable();
////        if (symTable.isDefined(var)) {
////            Value foundIndex = symTable.lookup(var);
////            if (foundIndex.getType() instanceof IntType) {
////                IntValue intIndex = (IntValue) foundIndex;
////
////                if (!semaphoreTable.isDefined(intIndex.getValue()))
////                    throw new MyException("Index not in the semaphore table");
////                Pair<Integer, List<Integer>> myPair = semaphoreTable.get(intIndex.getValue());
////                Integer N1 = myPair.getKey();
////                List<Integer> list1 = myPair.getValue();
////                int NL = list1.size();
////                if (N1 > NL) {
////                    if (!(list1.contains(state.getId())))
////                        list1.add(state.getId());
////                } else {
////                    state.getExeStack().push(this);
////                }
////
////            } else throw new MyException("Value not of IntType");
////
////
////        } else throw new MyException("Var not defined in the symbol table");
//        lock.lock();
//        try{
//            ISemaphoreTable<Integer,Pair<Integer, List<Integer>>> semTable = state.getSemaphoreTable();
//            IStack<IStmt> exeStack = state.getExeStack();
//            IntValue foundInteger = (IntValue) state.getSymTable().lookup("var");
//            if (foundInteger == null)
//                throw new MyException("Var not in the symbol table");
//            Pair<Integer, List<Integer>> semaphoreValue = semTable.get(foundInteger.getValue());
//            List<Integer> threads = semaphoreValue.getValue();
//            Integer nrMax = semaphoreValue.getKey();
//            if (nrMax != threads.size()){
//                if (threads.contains(state.getId()))
//                    throw new MyException("Already in process");
//                threads.add(state.getId());
//                //state.getSemaphoreTable().add();
//            }else {
//                exeStack.push(this);
//                state.setExeStack(exeStack);
//            }
//
//        }catch (Exception e){
//            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
//            alert.showAndWait();
//        }
//        finally {
//            lock.unlock();
//        }
//        //lock.unlock();
//        return null;
//    }
//
//    @Override
//    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws MyException {
//        return null;
//    }
//
//    @Override
//    public String toString() {
//        return "Acquire(" + var + ")";
//    }
//}
