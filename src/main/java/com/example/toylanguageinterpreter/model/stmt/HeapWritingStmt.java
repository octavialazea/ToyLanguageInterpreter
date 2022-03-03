package com.example.toylanguageinterpreter.model.stmt;

import com.example.toylanguageinterpreter.PrgState;
import com.example.toylanguageinterpreter.exceptions.ExpressionEvalException;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.exceptions.StatementExecException;
import com.example.toylanguageinterpreter.model.adt.IDict;
import com.example.toylanguageinterpreter.model.adt.IHeap;
import com.example.toylanguageinterpreter.model.exp.Exp;
import com.example.toylanguageinterpreter.model.types.RefType;
import com.example.toylanguageinterpreter.model.types.Type;
import com.example.toylanguageinterpreter.model.value.RefValue;
import com.example.toylanguageinterpreter.model.value.Value;

import java.io.FileNotFoundException;
import java.io.IOException;

public class HeapWritingStmt implements IStmt{

    private String var_name;
    private Exp expression;

    public HeapWritingStmt(String vn, Exp e){
        var_name = vn;
        expression = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException, IOException {
        IDict<String, Value> symTable = state.getSymTable();
        IHeap<Value> heap = state.getHeap();
        if (!symTable.isDefined(var_name))
            throw new MyException("Variable name is not defined in the SymbolTable!");
        Value val = symTable.lookup(var_name);
        if (!(val.getType() instanceof RefType))
            throw new MyException("Value not of RefType!");
        RefValue refValue = (RefValue) val;
        if (!heap.isDefined(refValue.getAddress()))
            throw new MyException("Address not defined in the heap!");
        Value exprEval = expression.eval(symTable, heap);
        if (!(exprEval.getType().equals(refValue.getLocationType())))
            throw new ExpressionEvalException("Expression evaluated to a value of an invalid type.");
        int address = refValue.getAddress();
        heap.update(address, exprEval);
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(var_name);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new StatementExecException("WRITE stmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString(){
        return "wH(" + var_name + "," + expression.toString() + ")";
    }
}
