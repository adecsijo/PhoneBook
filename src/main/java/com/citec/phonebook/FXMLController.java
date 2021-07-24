package com.citec.phonebook;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class FXMLController implements Initializable {
    
    private static final String MENU_CONTACTS = "Kontaktok";
    private static final String MENU_EXPORT = "Export";
    private static final String MENU_LIST = "Lista";
    private static final String MENU_EXIT = "Kilépés";
    
    private DB db;
    
    //<editor-fold defaultstate="collapsed" desc="FXML members">
    @FXML
    TableView table;
    @FXML
    TextField inputLastname;
    @FXML
    TextField inputFirstname;
    @FXML
    TextField inputPhone;
    @FXML
    Button addNewContactButton;
    @FXML
    Pane contactPane;
    @FXML
    Pane exportPane;
    @FXML
    StackPane menuPane;
    @FXML
    TextField inputExportName;
    @FXML
    Button exportButton;
//</editor-fold>
    
    private final ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("Kovács", "Gazsi", "+36501234567"),
            new Person("Kovács", "Babi", "+36598765432"),
            new Person("Nagy", "Pali", "+36567837655")
    );
    
    @FXML
    private void addContact(ActionEvent event) {
        String phone = inputPhone.getText();
        if (phone.length() >= 6 && phone.startsWith("+")) {
            Person person = new Person(inputLastname.getText(), inputFirstname.getText(), phone);
            data.add(person);
            inputLastname.clear();
            inputFirstname.clear();
            inputPhone.clear();
        }
    }
    
    private void setTableData() {
        TableColumn lastNameCol = new TableColumn("Vezetéknév");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        
        lastNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> event) {
                Person person = event.getTableView().getItems().get(event.getTablePosition().getRow());
                person.setLastName(event.getNewValue());
            }
        });
        
        TableColumn firstNameCol = new TableColumn("Keresztnév");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        
        firstNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> event) {
                Person person = event.getTableView().getItems().get(event.getTablePosition().getRow());
                person.setFirstName(event.getNewValue());
            }
        });
        
        TableColumn phoneCol = new TableColumn("Telefon");
        phoneCol.setMinWidth(100);
        phoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneCol.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));
        
        phoneCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> event) {
                Person person = event.getTableView().getItems().get(event.getTablePosition().getRow());
                person.setPhone(event.getNewValue());
            }
        });
        
        table.getColumns().addAll(lastNameCol, firstNameCol, phoneCol);
        data.addAll(db.findAll());
        table.setItems(data);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DB();
        setTableData();
        setMenuData();
    }    

    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menu");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);
        
        TreeItem<String> nodeItemA = new TreeItem<>(MENU_CONTACTS);
        TreeItem<String> nodeItemB = new TreeItem<>(MENU_EXIT);
        
        TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_LIST);
        TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_EXPORT);
        
        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2);
        
        treeItemRoot1.getChildren().addAll(nodeItemA, nodeItemB);
        
        menuPane.getChildren().add(treeView);
        
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
                TreeItem<String> selectedItem = newValue;
                String selectedMenu = selectedItem.getValue();
                if (null != selectedMenu) {
                    switch (selectedMenu) {
                        case MENU_CONTACTS:
                            selectedItem.setExpanded(true);
                            break;
                        case MENU_LIST:
                            contactPane.setVisible(true);
                            exportPane.setVisible(false);
                            break;
                        case MENU_EXPORT:
                            contactPane.setVisible(false);
                            exportPane.setVisible(true);
                            break;
                        case MENU_EXIT:
                            System.exit(0);
                            break;
                    }
                }
            }
        });
    }
}
