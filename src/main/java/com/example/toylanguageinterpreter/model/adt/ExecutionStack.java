package com.example.toylanguageinterpreter.model.adt;
import java.util.Stack;

public class ExecutionStack<T> implements IStack<T> {
    Stack<T> stack;

    public ExecutionStack() {
        stack = new Stack<T>();
    }

    public ExecutionStack(Stack<T> s){
        stack = s;
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public String toString() {
        return String.valueOf(stack);
    }

    @Override
    public ExecutionStack<T> deepCopy() {
        Stack<T> copyStack = new Stack<>();
        for (T elem: stack){
            copyStack.push(elem);
        }
        return new ExecutionStack<>(copyStack);
    }

    @Override
    public Stack<T> getStack() {
        return stack;
    }
}