package com.example.toylanguageinterpreter;

import com.example.toylanguageinterpreter.Controller.Controller;
import com.example.toylanguageinterpreter.Repo.Repo;
import com.example.toylanguageinterpreter.exceptions.StatementExecException;
import com.example.toylanguageinterpreter.model.adt.*;
import com.example.toylanguageinterpreter.model.exp.*;
import com.example.toylanguageinterpreter.model.stmt.*;
import com.example.toylanguageinterpreter.model.types.BoolType;
import com.example.toylanguageinterpreter.model.types.IntType;
import com.example.toylanguageinterpreter.model.types.RefType;
import com.example.toylanguageinterpreter.model.types.StringType;
import com.example.toylanguageinterpreter.model.value.BoolValue;
import com.example.toylanguageinterpreter.model.value.IntValue;
import com.example.toylanguageinterpreter.model.value.StringValue;
import com.example.toylanguageinterpreter.model.value.Value;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PrgListController implements Initializable {
    @FXML
    private ListView<IStmt> prgList;
    @FXML
    private Button loadButton;

    private static IStmt prg1, prg2, prg3, prg4, prg5, prg6, prg7, prg8, prg9, prg10;


     public void setUp() {
        prg1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));
        prg2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new
                                ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new CompStmt(new PrintStmt(new VarExp("b")), new PrintStmt(new VarExp("a")))))));
        prg3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));
        prg4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new openRFileStmt(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()),
                                new CompStmt(new readFileStmt(new VarExp("varf"), "varc"),
                                        new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new readFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")), new closeRFileStmt(new VarExp("varf"))))))
                        ))));
        prg5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new HeapAllocationStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new HeapAllocationStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));
         prg6 = new CompStmt(new VarDeclStmt("v", new IntType()),
                 new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                         new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                 new CompStmt(new HeapAllocationStmt("a", new ValueExp(new IntValue(22))),
                                         new CompStmt(new ForkStmt(new CompStmt(new HeapWritingStmt("a", new ValueExp(new IntValue(30))),
                                                 new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                         new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new HeapReadingExp(new VarExp("a"))))))),
                                                 new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new HeapReadingExp(new VarExp("a")))))))));

         prg7 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(
                 new AssignStmt("v", new ValueExp(new IntValue(4))),
                 new WhileStmt(new RelationalExp(">=", new VarExp("v"), new ValueExp(new IntValue(0))),
                         new CompStmt(new PrintStmt(new VarExp("v")),
                                 new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1))))))
                 ));

         prg8 = new CompStmt(new VarDeclStmt("v", new BoolType()), new AssignStmt("v", new ValueExp(new IntValue(4))));

         prg9 = new CompStmt(new VarDeclStmt("v", new IntType()),
                 new CompStmt(new VarDeclStmt("x", new IntType()),
                         new CompStmt(new VarDeclStmt("y", new IntType()),
                                 new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(0))),
                                         new CompStmt(new RepeatUntilStmt(new CompStmt(new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))), new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))))), new RelationalExp("==", new VarExp("v"), new ValueExp(new IntValue(3)))),
                                                 new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(1))),
                                                         new CompStmt(new NopStmt(),
                                                                 new CompStmt(new AssignStmt("y", new ValueExp(new IntValue(3))),
                                                                         new CompStmt(new NopStmt(), new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))))))))))));

         prg10 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                 new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                         new CompStmt(new VarDeclStmt("v3", new RefType(new IntType())),
                                 new CompStmt(new VarDeclStmt("cnt", new IntType()),
                                         new CompStmt(new HeapAllocationStmt("v1", new ValueExp(new IntValue(2))),
                                                 new CompStmt(new HeapAllocationStmt("v2", new ValueExp(new IntValue(3))),
                                                         new CompStmt(new HeapAllocationStmt("v3", new ValueExp(new IntValue(4))),
                                                                 new CompStmt(new NewBarrierStmt("cnt", new HeapReadingExp(new VarExp("v2"))),
                                                                         new CompStmt(new ForkStmt(new CompStmt(new AwaitStmt("cnt"), new CompStmt(new HeapWritingStmt("v1", new ArithExp('*', new HeapReadingExp(new VarExp("v1")), new ValueExp(new IntValue(10)))), new PrintStmt(new HeapReadingExp(new VarExp("v1")))))),
                                                                                 new CompStmt(new ForkStmt(new CompStmt(new AwaitStmt("cnt"),
                                                                                         new CompStmt(new HeapWritingStmt("v2", new ArithExp( '*',new HeapReadingExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                                                                 new CompStmt(new HeapWritingStmt("v2", new ArithExp( '*',new HeapReadingExp(new VarExp("v2")), new ValueExp(new IntValue(10)))), new PrintStmt(new HeapReadingExp(new VarExp("v2"))))))),
                                                                                         new CompStmt(new AwaitStmt("cnt"), new PrintStmt(new HeapReadingExp(new VarExp("v3"))))))))))))));

    }


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUp();
        ObservableList<IStmt> myData = FXCollections.observableArrayList();
        myData.add(prg1);
        myData.add(prg2);
        myData.add(prg3);
        myData.add(prg4);
        myData.add(prg5);
        myData.add(prg6);
        myData.add(prg7);
        myData.add(prg8);
        myData.add(prg9);
        myData.add(prg10);
        prgList.setItems(myData);
        prgList.getSelectionModel().selectFirst();
//        prgList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        loadButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent e) {
                Stage prgStage = new Stage();
                Parent programRoot;
                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == PrgRunController.class) {
                        ExecutionStack<IStmt> executionStack = new ExecutionStack<>();
                        Dict<String, Value> symbolTable = new Dict<>();
                        OutList<Value> outList = new OutList<>();
                        FileTable<StringValue, BufferedReader> fileTable = new FileTable<>();
                        IHeap<Value> heap = new HeapTable<>();
                        IBarrierTable<Integer, Pair<Integer, List<Integer>>> barrierTable = new BarrierTable<>();
                        IStmt stmt = prgList.getSelectionModel().getSelectedItem();
                        PrgState prgState = new PrgState(executionStack, symbolTable, outList, fileTable, heap, barrierTable, stmt);
                        Repo repo = new Repo(prgState,"log1.txt");
                        Controller ctrl = new Controller(repo);
                        ctrl.addProgram(prgState);
//                        try{
//                            stmt.typecheck(new Dict<>());
//                        }catch (StatementExecException ex){
//                            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
//                            alert.showAndWait();
//                            return null;
//                        }
                        return new PrgRunController(ctrl);
                    } else {
                        try {
                            return type.getDeclaredConstructor().newInstance();
                        } catch (Exception ex) {
                            System.err.println("\"Could not create controller for \"+type.getName()");
                            throw new RuntimeException(ex);
                        }
                    }
                };
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProgramLayout.fxml"));
                    fxmlLoader.setControllerFactory(controllerFactory);
                    programRoot = fxmlLoader.load();
                    Scene programScene = new Scene(programRoot);
                    prgStage.setTitle("Toy Language - Program running");
                    prgStage.setScene(programScene);
                    prgStage.show();
                } catch (IOException e1) {
                    System.out.println(e1.getMessage());
                }
            }
        });
    }
}