package com.example.toylanguageinterpreter.model.exp;

import com.example.toylanguageinterpreter.exceptions.ExpressionEvalException;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.exceptions.StatementExecException;
import com.example.toylanguageinterpreter.model.adt.IDict;
import com.example.toylanguageinterpreter.model.adt.IHeap;
import com.example.toylanguageinterpreter.model.types.BoolType;
import com.example.toylanguageinterpreter.model.types.IntType;
import com.example.toylanguageinterpreter.model.types.Type;
import com.example.toylanguageinterpreter.model.value.BoolValue;
import com.example.toylanguageinterpreter.model.value.IntValue;
import com.example.toylanguageinterpreter.model.value.Value;

import java.util.Objects;

public class RelationalExp implements Exp {

    private final Exp exp1;
    private final Exp exp2;
    private final String op;

    public RelationalExp(String o, Exp e1, Exp e2){
        op = o;
        exp1 = e1;
        exp2 = e2;
    }

    @Override
    public Value eval(IDict<String, Value> table, IHeap<Value> heap) throws MyException {
        Value v1, v2;
        v1 = exp1.eval(table, heap);
        if (v1.getType().equals(new IntType())){
            v2 = exp2.eval(table, heap);
            if (v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                if (Objects.equals(op, "<")) return new BoolValue(n1 < n2);
                if (Objects.equals(op, "<=")) return new BoolValue(n1 <= n2);
                if (Objects.equals(op, "==")) return new BoolValue(n1 == n2);
                if (Objects.equals(op, "!=")) return new BoolValue(n1 != n2);
                if (Objects.equals(op, ">")) return new BoolValue(n1 > n2);
                if (Objects.equals(op, ">=")) return new BoolValue(n1 >= n2);
            }else
                throw new ExpressionEvalException("second operand is not an integer");
        }else
            throw new ExpressionEvalException("first operand is not an integer");

        return new IntType().defaultValue();
    }

    public String getOp() { return this.op;}

    public Exp getExp1() {
        return exp1;
    }

    public Exp getExp2() {
        return exp2;
    }

    public String toString(){ return exp1.toString() + op + exp2.toString();}
    @Override
    public Exp deepCopy() {
        return new RelationalExp(op, exp1.deepCopy(), exp2.deepCopy());
    }

    @Override
    public Type typecheck(IDict<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1=exp1.typecheck(typeEnv);
        typ2=exp2.typecheck(typeEnv);
        if( typ1.equals(new IntType()) ){
            if (typ2.equals(new IntType())) {
                return new BoolType();
            } else
                throw new StatementExecException("second operand is not a boolean");
        }else
            throw new StatementExecException("first operand is not a boolean");
    }
}
