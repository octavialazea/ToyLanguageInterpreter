package com.example.toylanguageinterpreter.exceptions;

public class StatementExecException extends RuntimeException{
    public StatementExecException(String s){
        super(s);
    }
}
