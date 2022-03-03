package com.example.toylanguageinterpreter.model.stmt;

import com.example.toylanguageinterpreter.PrgState;
import com.example.toylanguageinterpreter.exceptions.ExpressionEvalException;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.exceptions.StatementExecException;
import com.example.toylanguageinterpreter.model.adt.IDict;
import com.example.toylanguageinterpreter.model.adt.IHeap;
import com.example.toylanguageinterpreter.model.adt.IStack;
import com.example.toylanguageinterpreter.model.exp.Exp;
import com.example.toylanguageinterpreter.model.types.BoolType;
import com.example.toylanguageinterpreter.model.types.RefType;
import com.example.toylanguageinterpreter.model.types.Type;
import com.example.toylanguageinterpreter.model.value.BoolValue;
import com.example.toylanguageinterpreter.model.value.RefValue;
import com.example.toylanguageinterpreter.model.value.Value;

public class IfStmt implements IStmt {

    Exp exp;
    IStmt thenS, elseS;

    public IfStmt(Exp ex, IStmt t, IStmt e){
        exp = ex;
        thenS = t;
        elseS = e;
    }
    @Override
    public PrgState execute(PrgState state) throws ExpressionEvalException {
        IStack<IStmt> stk = state.getExeStack();
        IHeap<Value> heap = state.getHeap();
        Value condition = exp.eval(state.getSymTable(), heap);
        if (!(condition.getType() instanceof BoolType))
            throw new ExpressionEvalException("Conditional expression is not a boolean!");
        if (((BoolValue)condition).getValue())
            stk.push(thenS);
        else
            stk.push(elseS);

        state.setExeStack(stk);
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.deepCopy());
            elseS.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new StatementExecException("The condition of IF has not the type bool");
    }

    public String toString(){ return "(IF "+ exp.toString()+" THEN " +thenS.toString()
            +" ELSE "+elseS.toString();}
}
