package com.example.toylanguageinterpreter.model.stmt;

import com.example.toylanguageinterpreter.PrgState;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.model.adt.IDict;
import com.example.toylanguageinterpreter.model.types.Type;

import java.io.FileNotFoundException;
import java.io.IOException;

public class NewLatchStmt implements IStmt{

    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws MyException {
        return null;
    }
}
