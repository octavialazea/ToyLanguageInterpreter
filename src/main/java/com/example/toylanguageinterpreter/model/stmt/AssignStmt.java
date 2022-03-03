package com.example.toylanguageinterpreter.model.stmt;

import com.example.toylanguageinterpreter.PrgState;
import com.example.toylanguageinterpreter.exceptions.ExpressionEvalException;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.exceptions.StatementExecException;
import com.example.toylanguageinterpreter.model.adt.IDict;
import com.example.toylanguageinterpreter.model.adt.IHeap;
import com.example.toylanguageinterpreter.model.exp.Exp;
import com.example.toylanguageinterpreter.model.types.Type;
import com.example.toylanguageinterpreter.model.value.Value;

public class AssignStmt implements IStmt {

    String id;
    Exp expression;

    public AssignStmt(String i, Exp exp) {
        id = i;
        expression = exp;
    }

    @Override
    public String toString() {
        return this.id + "=" + this.expression.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionEvalException {
        IDict<String, Value> symTable = state.getSymTable();
        IHeap<Value> heap = state.getHeap();
        if (symTable.isDefined(id)) {
            Value val = expression.eval(symTable, heap);
            Type typeId = (symTable.lookup(id)).getType();
            if (val.getType().equals(typeId))
                symTable.update(id, val);
            else throw new ExpressionEvalException("Declared type of variable " + id + " and type of the assigned expression do not match");
        } else throw new ExpressionEvalException("Variable " + id + " was not declared");
        return null;

    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(id);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new StatementExecException("Assignment: right hand side and left hand side have different types ");
    }

}
