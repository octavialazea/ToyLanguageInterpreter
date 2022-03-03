package com.example.toylanguageinterpreter.model.exp;

import com.example.toylanguageinterpreter.exceptions.ExpressionEvalException;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.exceptions.StatementExecException;
import com.example.toylanguageinterpreter.model.adt.IDict;
import com.example.toylanguageinterpreter.model.adt.IHeap;
import com.example.toylanguageinterpreter.model.types.BoolType;
import com.example.toylanguageinterpreter.model.types.Type;
import com.example.toylanguageinterpreter.model.value.BoolValue;
import com.example.toylanguageinterpreter.model.value.Value;

import java.util.Objects;

public class LogicExp implements Exp{

    private final Exp exp1;
    private final Exp exp2;
    private final String operand;

    public LogicExp(String o, Exp f, Exp s){
        operand = o;
        exp1 = f;
        exp2 = s;
    }

    @Override
    public Value eval(IDict<String, Value> symTable, IHeap<Value> heap) throws ExpressionEvalException {
        Value v1, v2;
        v1 = exp1.eval(symTable, heap);
        if (v1.getType().equals(new BoolType())){
            v2 = exp2.eval(symTable, heap);
            if (v2.getType().equals(new BoolType())){
                BoolValue b1 = (BoolValue)v1;
                BoolValue b2 = (BoolValue)v2;
                boolean first = b1.getValue();
                boolean second = b2.getValue();
                if (Objects.equals(operand, "AND")) return new BoolValue(first && second);
                if (Objects.equals(operand, "OR")) return new BoolValue(first || second);
//                if (operand == 3) return new BoolValue(first ^ second);
//                if (operand == 4) return new BoolValue(first != second);
            }
            else
                throw new ExpressionEvalException("The second operand is not boolean");
        }
        else
            throw new ExpressionEvalException("The first operand is not boolean");

        return new BoolValue(false);
    }

    @Override
    public String toString() {
        return exp1.toString() + " " + operand + " " + exp2.toString();
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(operand, exp1.deepCopy(), exp2.deepCopy());
    }

    @Override
    public Type typecheck(IDict<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1=exp1.typecheck(typeEnv);
        typ2=exp2.typecheck(typeEnv);
        if( typ1.equals(new BoolType()) ){
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new StatementExecException("second operand is not a boolean");
        }else
            throw new StatementExecException("first operand is not a boolean");
    }
}
