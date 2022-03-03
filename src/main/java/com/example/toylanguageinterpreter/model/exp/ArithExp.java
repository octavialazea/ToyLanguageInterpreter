package com.example.toylanguageinterpreter.model.exp;


import com.example.toylanguageinterpreter.exceptions.ExpressionEvalException;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.exceptions.StatementExecException;
import com.example.toylanguageinterpreter.model.adt.IDict;
import com.example.toylanguageinterpreter.model.adt.IHeap;
import com.example.toylanguageinterpreter.model.types.IntType;
import com.example.toylanguageinterpreter.model.types.Type;
import com.example.toylanguageinterpreter.model.value.IntValue;
import com.example.toylanguageinterpreter.model.value.Value;

public class ArithExp implements Exp{

    private final char op;
    private final Exp e1;
    private final Exp e2;

    public ArithExp(char operand, Exp exp1, Exp exp2){
        op = operand;
        e1 = exp1;
        e2 = exp2;
    }

    public Value eval(IDict<String, Value> table, IHeap<Value> heap) throws ExpressionEvalException{
        Value v1, v2;
        v1 = e1.eval(table, heap);
        if (v1.getType().equals(new IntType())){
            v2 = e2.eval(table, heap);
            if (v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                if (op == '+') return new IntValue(n1+n2);
                if (op == '-') return new IntValue(n1-n2);
                if (op == '*') return new IntValue(n1*n2);
                if (op == '/')
                    if (n2 == 0) throw new ExpressionEvalException("division by zero");
                    else return new IntValue(n1/n2);
            }else
                throw new ExpressionEvalException("second operand is not an integer");
        }else
            throw new ExpressionEvalException("first operand is not an integer");

        return new IntValue(0);
    }

    public char getOp() {return this.op;}

    public Exp getFirst() {
        return this.e1;
    }

    public Exp getSecond() {
        return this.e2;
    }

    public String toString() {
        return e1.toString() + op + e2.toString();
    }

    @Override
    public Exp deepCopy() {
        return new ArithExp(op, e1.deepCopy(), e2.deepCopy());
    }

    @Override
    public Type typecheck(IDict<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if( typ1.equals(new IntType()) ){
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else
                throw new StatementExecException("ArExp: second operand is not an integer");
        }else
            throw new StatementExecException("ArExp: first operand is not an integer");
    }

}
