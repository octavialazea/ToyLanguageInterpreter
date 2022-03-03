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
import java.io.IOException;

public class closeRFileStmt implements IStmt{

    Exp exp;

    public closeRFileStmt(Exp e) { exp = e; }

    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
        //state.getExeStack().pop();
        IHeap<Value> heap = state.getHeap();
        Value val = exp.eval(state.getSymTable(), heap);
        if (!(val.getType() instanceof StringType))
            throw new ExpressionEvalException("Value not of StringType");
        if (!state.getFileTable().isDefined((StringValue) val))
            throw new MyException("Value not defined in the FileTable");
        BufferedReader reader = state.getFileTable().lookup((StringValue)val);
        reader.close();
        state.getFileTable().remove((StringValue) val);
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new StringType()))
        {
            return typeEnv;
        }else
            throw new StatementExecException("CloseFile: Not a string type!");
    }

    @Override
    public String toString(){
        return "closeRFile(" + exp.toString() + ")";
    }
}
