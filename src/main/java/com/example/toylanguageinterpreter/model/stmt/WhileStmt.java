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
import com.example.toylanguageinterpreter.model.types.Type;
import com.example.toylanguageinterpreter.model.value.BoolValue;
import com.example.toylanguageinterpreter.model.value.Value;

import java.io.IOException;

public class WhileStmt implements IStmt{

    private Exp exp;
    private IStmt stmt;

    public WhileStmt(Exp e, IStmt s){
        exp = e;
        stmt = s;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        IStack<IStmt> stk = state.getExeStack();
        IHeap<Value> heap = state.getHeap();
        Value val = exp.eval(state.getSymTable(), heap);
        if (val.getType() instanceof BoolType){
//            if (!((BoolValue)val).getValue())
//                stk.pop();
//            else{
//                stk.push(new WhileStmt(exp, stmt));
//                stk.push(stmt);
//            }
            if(((BoolValue)val).getValue()){
                stk.push(new WhileStmt(exp, stmt));
                stk.push(stmt);
            }
        }
        else
            throw new ExpressionEvalException("Value not of BoolType");
        state.setExeStack(stk);
        return null;
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())){
            stmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new StatementExecException("The condition of WHILE does not have the type bool");
    }

    @Override
    public String toString(){
        return "WHILE(" + exp.toString() + ")" + "{" + stmt.toString() + "}";
    }

}
