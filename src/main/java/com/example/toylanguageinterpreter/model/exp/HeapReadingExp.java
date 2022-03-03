package com.example.toylanguageinterpreter.model.exp;

import com.example.toylanguageinterpreter.exceptions.ExpressionEvalException;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.exceptions.StatementExecException;
import com.example.toylanguageinterpreter.model.adt.IDict;
import com.example.toylanguageinterpreter.model.adt.IHeap;
import com.example.toylanguageinterpreter.model.types.RefType;
import com.example.toylanguageinterpreter.model.types.Type;
import com.example.toylanguageinterpreter.model.value.RefValue;
import com.example.toylanguageinterpreter.model.value.Value;

public class HeapReadingExp implements Exp{

    private Exp exp;

    public HeapReadingExp(Exp e){
        exp = e;
    }

    @Override
    public Value eval(IDict<String, Value> symTable, IHeap<Value> heap) throws MyException {
        Value val = exp.eval(symTable, heap);
        if (!(val.getType() instanceof RefType))
            throw new ExpressionEvalException("Value is not a RefValue");
        RefValue refValue = (RefValue) val;
        int address = refValue.getAddress();
        if (!(heap.isDefined(address)))
            throw new MyException("Address not defined in the heap");
        Value heapVal = heap.getValue(address);
        return heapVal;
    }

    @Override
    public Exp deepCopy() {
        return new HeapReadingExp(exp);
    }

    @Override
    public Type typecheck(IDict<String, Type> typeEnv) throws MyException {
        Type typ=exp.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new StatementExecException("the rH argument is not a Ref Type");
    }

    @Override
    public String toString(){
        return "rH(" + exp.toString() + ")";
    }
}
