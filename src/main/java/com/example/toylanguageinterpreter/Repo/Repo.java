package com.example.toylanguageinterpreter.Repo;
import com.example.toylanguageinterpreter.PrgState;
import com.example.toylanguageinterpreter.exceptions.ExpressionEvalException;
import com.example.toylanguageinterpreter.exceptions.MyException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repo implements IRepo {

    private List<PrgState> prgStates;
    private  String logFilePath;

    public Repo(PrgState s, String path) {
        prgStates = new ArrayList<>();
        logFilePath = path;
        //prgStates.add(s);
    }

    public Repo(String path) {
        prgStates = new ArrayList<>();
        logFilePath = path;
    }

    public Repo(){
        logFilePath = "repoLog.txt";
    }


    @Override
    public void nextPrg(){
        ArrayList<PrgState> newList = new ArrayList<>();
        if (prgStates.isEmpty())
            throw new ExpressionEvalException("Empty list");
        for (int i = 1; i < prgStates.size(); i++)
            newList.add(prgStates.get(i));
        prgStates = newList;
    }

    @Override
    public PrgState getMainPrg() {
        return prgStates.get(0);
    }

    @Override
    public void addPrg(PrgState prg) {
        prgStates.add(prg);
    }

    @Override
    public List<PrgState> getPrgList() { return prgStates; }

    @Override
    public void setPrgList(List<PrgState> states) {
        prgStates = states;
    }

    @Override
    public PrgState getPrgStateWithId(int id){
        PrgState ps = null;
        for (PrgState s : prgStates)
            if (s.getId() == id)
                ps = s;
        return ps;
    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws MyException, IOException {
        try
        {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            pw.println("-------BEGIN-----");
            pw.println("--ID--");
            pw.println("\t"+prgState.getId());
            pw.println("--Execution Stack--");
            pw.println("\t"+prgState.getExeStack().toString());
            pw.println("--Symbol Table--");
            pw.println("\t"+prgState.getSymTable().toString());
            pw.println("--Output List--");
            pw.println("\t"+prgState.getOutList().toString());
            pw.println("--File Table--");
            pw.println("\t"+prgState.getFileTable().toString());
            pw.println("--Heap--");
            pw.println("\t"+prgState.getHeap().toString());
            pw.println("--------END--------");
            pw.close();
        }catch(IOException e){
            throw new MyException(e.getMessage());
        }

    }

    // OLD VERSION
//    public void logPrgStateExec() throws MyException, IOException {
//       try
//       {
//           PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
////           System.out.println(prgStates.get(0).toString());
//           pw.println(">>");
//           pw.println("--ID--");
//           pw.println("\t"+prgStates.get(0).getId());
//           pw.println("--Execution Stack--");
//           pw.println("\t"+prgStates.get(0).getExeStack().toString());
//           pw.println("--Symbol Table--");
//           pw.println("\t"+prgStates.get(0).getSymTable().toString());
//           pw.println("--Output List--");
//           pw.println("\t"+prgStates.get(0).getOutList().toString());
//           pw.println("--File Table--");
//           pw.println("\t"+prgStates.get(0).getFileTable().toString());
//           pw.println("--Heap--");
//           pw.println("\t"+prgStates.get(0).getHeap().toString());
//           pw.println(">>");
//           pw.close();
//       }catch(IOException e){
//           throw new MyException(e.getMessage());
//       }
//
//    }
}
