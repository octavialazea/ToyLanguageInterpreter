package com.example.toylanguageinterpreter;

import com.example.toylanguageinterpreter.Controller.Controller;
import com.example.toylanguageinterpreter.exceptions.AdtException;
import com.example.toylanguageinterpreter.exceptions.MyException;
import com.example.toylanguageinterpreter.model.value.StringValue;
import com.example.toylanguageinterpreter.model.value.Value;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class PrgRunController {

    Controller ctrl;
    @FXML
    TextField nrPrgStates;
    @FXML
    Button runButton;
    @FXML
    ListView<String> prgStateList;
    @FXML
    TableView<HashMap.Entry<Integer, Value>> heapTable;
    @FXML
    TableColumn<HashMap.Entry<Integer, Value>, String> heapTableAddress;
    @FXML
    TableColumn<HashMap.Entry<Integer, Value>, String> heapTableValue;
    @FXML
    ListView<String> outList;
    @FXML
    ListView<String> fileTable;
    @FXML
    TableView<HashMap.Entry<String, Value>> symTable;
    @FXML
    TableColumn<HashMap.Entry<String, Value>, String> symTableVariable;
    @FXML
    TableColumn<HashMap.Entry<String, Value>, String> symTableValue;
    @FXML
    ListView<String> exeStackList;
    @FXML
    TableView<HashMap.Entry<Integer, Pair<Integer, List<Integer>>>> barrierTable;
    @FXML
    TableColumn<HashMap.Entry<Integer, Pair<Integer, List<Integer>>>, String> barrierTableIndex;
    @FXML
    TableColumn<HashMap.Entry<Integer, Pair<Integer, List<Integer>>>, String> barrierTableValue;
    @FXML
    TableColumn<HashMap.Entry<Integer, Pair<Integer, List<Integer>>>, String> barrierTableListValues;

    public PrgRunController(Controller c){
        ctrl = c;
    }
    @FXML
    public void initialize() {
        initialRun();
        ctrl.setExecutor(Executors.newFixedThreadPool(3));

        prgStateList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setSymTableAndExeStack();
            }
        });
        runButton.setOnAction(actionEvent -> {
            try{
                List<PrgState> prgList = ctrl.removeCompletedPrg(ctrl.getRepository().getPrgList());
                oneStep(prgList);
//                try
//                {
//                    ctrl.oneStepForAllGUI();
//                }catch (IOException | InterruptedException e){
//                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
//                    alert.showAndWait();
//                    runButton.setDisable(true);
//                    return;
//                }
                update();
                setPrgStateList();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Toy Language - Current finished");
                alert.setHeaderText(null);
                alert.setContentText("Program successfully finished!");
                Button confirm = (Button)alert.getDialogPane().lookupButton(ButtonType.OK);
                confirm.setDefaultButton(false);
                confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                alert.showAndWait();
                Stage stage = (Stage) heapTable.getScene().getWindow();
                stage.close();
            }
//            update();
        });
    }
private void oneStep(List<PrgState> prgList){

        if (ctrl == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The program was not selected", ButtonType.OK);
            alert.showAndWait();
        }
        try{
            if(ctrl.getRepository().getPrgList().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute!", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            if(ctrl.getRepository().getPrgList().get(0).getExeStack().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Execution stack is empty!", ButtonType.OK);
                alert.showAndWait();
            }
            try{
//                List<PrgState> programs = ctrl.removeCompletedPrg(ctrl.getRepository().getPrgList());
//                ctrl.oneStepForAllPrograms(programs);
//                prgList.get(0).getHeap().setContent((HashMap<Integer, Value>) ctrl.garbageCollector(ctrl.getAddrFromSymTable(prgList), prgList.get(0).getHeap().getContent()));
//                ctrl.oneStepForAllPrograms(prgList);
                ctrl.oneStepForAllGUI();
                update();
                setPrgStateList();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
//                runButton.setDisable(true);
//                return;
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            }

}
    private void initialRun( ) {
        setNumberLabel();
        setHeapTable();
        setOutList();
        setFileTable();
        setPrgStateList();
        setBarrierTable();
        prgStateList.getSelectionModel().selectFirst();
        setSymTableAndExeStack();
    }

    public void update(){
        setNumberLabel();
        setHeapTable();
        setOutList();
        setFileTable();
        setBarrierTable();
        if (prgStateList.getSelectionModel().getSelectedItems() == null)
            prgStateList.getSelectionModel().selectFirst();
        setSymTableAndExeStack();
    }

    private void setFileTable(){
        ObservableList<String> fileTableList = FXCollections.observableArrayList();
        for (Map.Entry<StringValue, BufferedReader> e: ctrl.getRepository().getPrgList().get(0).getFileTable().getTable().entrySet())
            fileTableList.add(e.getKey().getValue());
//        for (PrgState p : ctrl.getRepository().getPrgList())
//            fileTableList.add(String.valueOf(p.getHeap().getContent().entrySet()));
        fileTable.setItems(fileTableList);
        fileTable.refresh();
    }

    private void setSymTableAndExeStack() {
        ObservableList<HashMap.Entry<String, Value>> symTableList = FXCollections.observableArrayList();
        ObservableList<String> exeStackItemsList = FXCollections.observableArrayList();
        symTableVariable.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey()));
        symTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        PrgState crtPrg = null;
        for(PrgState e : ctrl.getRepository().getPrgList()) {
            if(e.getId() == Integer.parseInt(prgStateList.getSelectionModel().getSelectedItem())) {
                crtPrg = e;
                break;
            }
        }

        assert crtPrg != null;
        String[] result = crtPrg.getExeStack().getStack().toString().split(";");

        if(crtPrg != null){
            symTableList.addAll(crtPrg.getSymTable().getContent().entrySet());
//            for(IStmt e : crtPrg.getExeStack().getStack()){
//                exeStackItemsList.add(e.toString());
//            }
            exeStackItemsList.addAll(Arrays.asList(result));

            symTable.setItems(symTableList);
            exeStackList.setItems(exeStackItemsList);
        }
        symTable.refresh();
        exeStackList.refresh();

    }
    private void setHeapTable(){
        ObservableList<HashMap.Entry<Integer, Value>> heapTableList = FXCollections.observableArrayList();
        heapTableAddress.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
        heapTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        try
        {
            heapTableList.addAll(ctrl.getRepository().getPrgList().get(0).getHeap().getContent().entrySet());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        heapTable.setItems(heapTableList);
        heapTable.refresh();
    }

    private void setPrgStateList() {
        ObservableList<String> prgStateIdList = FXCollections.observableArrayList();
        //prgStateIdList.addAll(Integer.toString(ctrl.getRepository().getPrgList().get(0).getId()));
        for (PrgState p: ctrl.getRepository().getPrgList())
            prgStateIdList.add(Integer.toString(p.getId()));
        prgStateList.setItems(prgStateIdList);
        prgStateList.refresh();
    }

    private void setOutList(){
        ObservableList<String> outObservableList = FXCollections.observableArrayList();
        for(Value e : ctrl.getRepository().getPrgList().get(0).getOutList().getList())
            outObservableList.add(e.toString());
//        for (PrgState p:ctrl.getRepository().getPrgList()){
//            outObservableList.add(p.getOutList().getList().toString());
//        }
        outList.setItems(outObservableList);
        outList.refresh();
    }

    private void setNumberLabel() {
        nrPrgStates.setText("The number of PrgStates: " + ctrl.getRepository().getPrgList().size());
    }

    private void setBarrierTable(){
        ObservableList<HashMap.Entry<Integer, Pair<Integer, List<Integer>>>> barrierTableList = FXCollections.observableArrayList();
        barrierTableIndex.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
        barrierTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getKey().toString()));
        barrierTableListValues.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getValue().toString()));
        try
        {
            barrierTableList.addAll(ctrl.getRepository().getPrgList().get(0).getBarrierTable().getContent().entrySet());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        barrierTable.setItems(barrierTableList);
        barrierTable.refresh();
    }


}
