package com.example.toylanguageinterpreter.model.stmt;

import com.example.toylanguageinterpreter.PrgState;
import com.example.toylanguageinterpreter.exceptions.ExpressionEvalException;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.exceptions.StatementExecException;
import com.example.toylanguageinterpreter.model.adt.IDict;
import com.example.toylanguageinterpreter.model.adt.IHeap;
import com.example.toylanguageinterpreter.model.exp.Exp;
import com.example.toylanguageinterpreter.model.types.StringType;
import com.example.toylanguageinterpreter.model.types.Type;
import com.example.toylanguageinterpreter.model.value.StringValue;
import com.example.toylanguageinterpreter.model.value.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class openRFileStmt implements IStmt{

    private Exp exp;

    public openRFileStmt(Exp e){
        exp = e;
    }

    public openRFileStmt(String f, String s) {
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException {
        //state.getExeStack().pop();
        IHeap<Value> heap = state.getHeap();
        Value val = exp.eval(state.getSymTable(), heap);
        if (!(val.getType() instanceof StringType))
            throw new ExpressionEvalException("Expression type is not StringType");
        if (state.getFileTable().isDefined((StringValue) val))
            throw new MyException("Value already defined in FileTable");
        BufferedReader reader = new BufferedReader(new FileReader(((StringValue) val).getValue()));
        state.getFileTable().add(((StringValue) val), reader);
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new StringType()))
        {
            return typeEnv;
        }else
            throw new StatementExecException("OpenFile: Not a string type!");
    }

    @Override
    public String toString(){
        return "openRFile(" + exp.toString() + ")";
    }

}
