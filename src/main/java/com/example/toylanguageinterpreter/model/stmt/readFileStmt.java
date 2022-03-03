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
import com.example.toylanguageinterpreter.model.value.*;

import java.io.BufferedReader;
import java.io.IOException;

public class readFileStmt implements IStmt {

    private Exp exp;
    private String var_name;

    public readFileStmt(Exp e, String n) {
        exp = e;
        var_name = n;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        IHeap<Value> heap = state.getHeap();
        if (!state.getSymTable().isDefined(var_name)) {
            throw new ExpressionEvalException("Name not defined in the SymbolTable");
        }
        Value val = exp.eval(state.getSymTable(), heap);
        if (!(val.getType() instanceof StringType)) {
            throw new MyException("Not a string type");
        }
        BufferedReader reader = state.getFileTable().lookup((StringValue) val);
        String line = reader.readLine();
        int i;
        try{
            i = Integer.parseInt(line);
        }catch (Exception e){
            i = 0;
        }
//        if (line == null) {
//            intValue = new IntValue(0);
//        } else {
//            i = Integer.parseInt(line);
//            intValue = new IntValue(i);
//        }
        IntValue intValue = new IntValue(i);
        state.getSymTable().update(var_name, intValue);
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new StringType()))
        {
            return typeEnv;
        }else
            throw new StatementExecException("ReadFile: Not a string type!");
    }

    @Override
    public String toString(){
        return "readFile(" + exp.toString() + ")";
    }
}
