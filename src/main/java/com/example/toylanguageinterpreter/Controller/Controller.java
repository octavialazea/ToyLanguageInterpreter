package com.example.toylanguageinterpreter.Controller;

import com.example.toylanguageinterpreter.PrgState;
import com.example.toylanguageinterpreter.Repo.IRepo;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.model.value.RefValue;
import com.example.toylanguageinterpreter.model.value.Value;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {

    private final IRepo repo;
    private ExecutorService executor;

    public Controller(IRepo r){
        repo = r;
    }

    public IRepo getRepository() {
        return repo;
    }

    public void addProgram(PrgState newPrg) {
        repo.addPrg(newPrg);
    }

    public void setExecutor(ExecutorService e) { executor = e;}

    public List<Integer> getAddrFromSymTable(List<PrgState> prgStateList){
//        return symTableValues.stream()
//                .filter(v -> v instanceof RefValue)
//                .map(value -> {RefValue v2 = (RefValue) value;
//                return v2.getAddress();})
//                .collect(Collectors.toList());
        return prgStateList.stream()
                .flatMap(pair -> pair.getSymTable().getContent().values().stream()
                        .filter(val -> val instanceof RefValue)
                        .map(val -> ((RefValue)val).getAddress()))
                .collect(Collectors.toList());
    }


    public Map<Integer, Value> garbageCollector(List<Integer> symTableAddresses, HashMap<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrograms(List<PrgState> programs) {
           programs.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });
        List<Callable<PrgState>> callList = programs.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = null;
        try{
            newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try{
                            return future.get();
                        }catch (InterruptedException | ExecutionException e){
                            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                            alert.showAndWait();
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        }catch (MyException | InterruptedException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        assert newPrgList != null;
        programs.addAll(newPrgList);

        programs.forEach(prg -> {
            try {
                try
                {
                    repo.logPrgStateExec(prg);
                }catch (MyException e){
                    System.out.println(e.getMessage());
                }
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });
        repo.setPrgList(programs);
    }


    public void allStep() {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgStateList = removeCompletedPrg(repo.getPrgList());
        while (prgStateList.size() > 0){
            prgStateList.get(0).getHeap().setContent((HashMap<Integer, Value>) garbageCollector(getAddrFromSymTable(prgStateList), prgStateList.get(0).getHeap().getContent()));
            oneStepForAllPrograms(prgStateList);
            prgStateList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgStateList);
    }

    public void oneStepForAllGUI() throws IOException, InterruptedException {
        List<PrgState> list = removeCompletedPrg(repo.getPrgList());
        executor = Executors.newFixedThreadPool(2);
        for (PrgState state : list)
            repo.logPrgStateExec(state);
        List<Callable<PrgState>> callList = list.stream()
                .map(p -> (Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());
        List<PrgState> newList = executor.invokeAll(callList).stream()
                .map(future -> { try { return future.get();}
                catch (Exception e) { throw new MyException(e.getMessage());}})
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        list.addAll(newList);
        for (PrgState prgState : list)
            repo.logPrgStateExec(prgState);

        HashMap<Integer,Value> newHeap = (HashMap<Integer, Value>) garbageCollector(getAddrFromSymTable(repo.getPrgList()), repo.getPrgList().get(0).getHeap().getContent());
        repo.getPrgList()
                .forEach(prg -> prg.getHeap().setContent(newHeap));
        repo.setPrgList(list);
    }


}

//OLD VERSIONS

//    List<Integer> getAddrFromHeap(Collection<Value> heapValues){
//        return heapValues.stream()
//                .filter(v -> v instanceof RefValue)
//                .map(value -> {RefValue v2 = (RefValue) value;
//                    return v2.getAddress();})
//                .collect(Collectors.toList());
//
//    }
//    public PrgState oneStep(PrgState state) throws StatementExecException, IOException {
//        IStack<IStmt> stk = state.getExeStack();
//        if (stk.isEmpty())
//            throw new StatementExecException("PrgState is empty!");
//        IStmt crtStmt = stk.pop();
//        //System.out.format(crtStmt.toString() + "\n");
//        return crtStmt.execute(state);
//    }

//    public void allStep(){
//        if (repo.getAll().isEmpty())
//            throw new ExpressionEvalException("Nothing more to execute");
//       PrgState prg = repo.getCrtPrg();
//        while (!prg.getExeStack().isEmpty())
//        {
//            oneStep(prg);
//            System.out.println("\n" + prg.toString());
//
//        }
//        System.out.println("\nResult: " + prg.getOutList().toString());
//        try{
//            repo.nextPrg();
//        }catch (ExpressionEvalException e){
//            System.out.println(e.getMessage());
//        }
//    }

//    public List<Integer> getAddressFromTables(List<PrgState> programs){
//        programs.stream()
//                .flatMap(prgState -> prgState.getSymTable().getContent().stream())
//                .collect(Collectors.toList())
//                .stream()
//                .filter(element  -> element instanceof RefValue)
//                .map(element -> ((RefValue) element).getAddress())
//                .collect(Collectors.toList());
//    }
//

//    public void allStep() throws MyException, IOException {
//        PrgState prg = repo.getPrgList().get(0);
//        repo.logPrgStateExec(prg);
//        while (!prg.getExeStack().isEmpty()){
//            prg.oneStep();
//            repo.logPrgStateExec(prg);
//            prg.getHeap().setContent((HashMap<Integer, Value>) unsafeGarbageCollector(
//                    getAddrFromSymTable(prg.getSymTable().getContent().values()), getAddrFromHeap(prg.getHeap().getContent().values()),
//                    prg.getHeap().getContent()));
//            repo.logPrgStateExec(prg);
//        }
//        System.out.println("Result: " + prg.getOutList().toString());
//    }



//    public void allStep() throws MyException, IOException {
//        PrgState prg = repo.getPrgList().get(0);
//        repo.logPrgStateExec(prg);
//        while (!prg.getExeStack().isEmpty()){
//            prg.oneStep();
//            repo.logPrgStateExec(prg);
//        }
//        System.out.println("Result: " + prg.getOutList().toString());
//    }
