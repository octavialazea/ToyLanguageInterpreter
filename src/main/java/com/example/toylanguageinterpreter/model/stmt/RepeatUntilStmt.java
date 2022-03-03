package com.example.toylanguageinterpreter.model.stmt;

import com.example.toylanguageinterpreter.PrgState;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.exceptions.StatementExecException;
import com.example.toylanguageinterpreter.model.adt.IDict;
import com.example.toylanguageinterpreter.model.adt.IStack;
import com.example.toylanguageinterpreter.model.exp.Exp;
import com.example.toylanguageinterpreter.model.exp.RelationalExp;
import com.example.toylanguageinterpreter.model.types.BoolType;
import com.example.toylanguageinterpreter.model.types.Type;

import java.io.IOException;

public class RepeatUntilStmt implements IStmt {

    private IStmt stmt1;
    private Exp exp2;

    public RepeatUntilStmt(IStmt s, Exp e) {
        stmt1 = s;
        exp2 = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        IStack<IStmt> stk = state.getExeStack();
        RelationalExp newExp2 = null;
        RelationalExp rel = (RelationalExp) exp2;
        if (rel.getOp() == "<") {
            newExp2 = new RelationalExp(">=", rel.getExp1(), rel.getExp2());
        }
        if (rel.getOp() == "<=") {
            newExp2 = new RelationalExp(">", rel.getExp1(), rel.getExp2());
        }
        if (rel.getOp() == ">") {
            newExp2 = new RelationalExp("<=", rel.getExp1(), rel.getExp2());
        }
        if (rel.getOp() == ">=") {
            newExp2 = new RelationalExp("<", rel.getExp1(), rel.getExp2());
        }
        if (rel.getOp() == "==") {
            newExp2 = new RelationalExp("!=", rel.getExp1(), rel.getExp2());
        }
        if (rel.getOp() == "!=") {
            newExp2 = new RelationalExp("==", rel.getExp1(), rel.getExp2());
        }

        IStmt newStmt = new CompStmt(stmt1, new WhileStmt(newExp2, stmt1));
        stk.push(newStmt);
        state.setExeStack(stk);
        return null;
}

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnv) throws MyException {
        Type typexp = exp2.typecheck(typeEnv);
        if (typexp.equals(new BoolType())){
            stmt1.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new StatementExecException("The condition of RepeatUntil does not have bool type");
    }

    @Override
    public String toString() {
        return "repeat (" + stmt1 + ")" + "until (" + exp2 + ")";
    }
}
