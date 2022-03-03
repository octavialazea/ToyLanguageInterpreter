package com.example.toylanguageinterpreter;

import com.example.toylanguageinterpreter.exceptions.AdtException;
import com.example.toylanguageinterpreter.model.adt.*;
import com.example.toylanguageinterpreter.model.stmt.IStmt;
import com.example.toylanguageinterpreter.model.value.StringValue;
import com.example.toylanguageinterpreter.model.value.Value;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class PrgState {

    private IStack<IStmt> exeStack;
    private IDict<String, Value> symTable;
    private IList<Value> outList;
    private IStmt originalProgram;
    private IFileTable<StringValue, BufferedReader> fileTable;
    private IHeap<Value> heap;
    private IBarrierTable<Integer, Pair<Integer, List<Integer>>> barrierTable;
    private int id;
    static int lastID = 0;

    public PrgState(IStack<IStmt> stk, IDict<String, Value> symtbl, IList<Value> out, IFileTable<StringValue, BufferedReader> ftable, IHeap<Value> hp, IBarrierTable<Integer, Pair<Integer, List<Integer>>> barTable, IStmt prg){
        exeStack = stk;
        symTable = symtbl;
        outList = out;
        fileTable = ftable;
        originalProgram = prg;
        heap = hp;
        barrierTable = barTable;
        exeStack.push(prg);
        id = getNewID();
    }

    public IDict<String, Value> getSymTable() {
        return symTable;
    }
    public IStack<IStmt> getExeStack() { return exeStack; }
    public IList<Value> getOutList() { return outList; }
    public IFileTable<StringValue, BufferedReader> getFileTable() { return fileTable; }
    public IHeap<Value> getHeap(){ return heap; }
    public IBarrierTable<Integer, Pair<Integer, List<Integer>>> getBarrierTable(){return barrierTable;}


    public int getId(){return id;}

    public void setExeStack(IStack<IStmt> stk){ exeStack = stk; }
    public void setSymTable(IDict<String, Value> table){ symTable = table; }
    public void setOutList(IList<Value> out){ outList = out; }
    public void setFileTable(IFileTable<StringValue, BufferedReader> ftable){ fileTable = ftable;}
    public void setHeap(IHeap<Value> hp) { heap = hp;}
    public void setId(int i) { id = i;}



    @Override
    public String toString() {
        return "id: " + id + "\nExecState: " + exeStack.toString() + "\nSymTable: " + symTable.toString() + "\nOutList: " + outList.toString()
                + "\nFileTable: " + fileTable.toString() + "\nHeap: " + heap.toString();
    }

    public Boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws AdtException, IOException {
        if (exeStack.isEmpty())
            throw new AdtException("PrgState stack is empty!");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public synchronized int getNewID() {
        ++lastID;
        return lastID;
    }
}