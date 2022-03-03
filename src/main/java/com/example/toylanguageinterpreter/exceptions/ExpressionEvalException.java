package com.example.toylanguageinterpreter.exceptions;

public class ExpressionEvalException extends RuntimeException{
    public ExpressionEvalException(String s){
        super(s);
    }
}
