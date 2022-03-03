package com.example.toylanguageinterpreter.model.adt;

import java.util.Stack;

public interface IStack<T> {

    public T pop();
    public void push(T v);
    public boolean isEmpty();
    public String toString();
    ExecutionStack<T> deepCopy();
    Stack<T> getStack();
}

