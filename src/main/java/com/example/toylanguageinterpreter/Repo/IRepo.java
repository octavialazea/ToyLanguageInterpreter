package com.example.toylanguageinterpreter.Repo;
import com.example.toylanguageinterpreter.PrgState;
import com.example.toylanguageinterpreter.exceptions.MyException;

import java.io.IOException;
import java.util.List;

public interface IRepo {
    void addPrg(PrgState newPrg);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> states);
    void nextPrg();
    PrgState getMainPrg();
    void logPrgStateExec(PrgState prgState) throws MyException, IOException;
    PrgState getPrgStateWithId(int id);
}
