/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glc.view;

import glc.MainTcomp;
import glc.model.Simbols;
import java.util.ArrayList;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * FXML Controller class
 *
 * @author laryssamuniz
 */
public class SimbolsOverviewController {

    @FXML
    private TextField inputTranstion;

    @FXML
    private TableView<Simbols> simbolsTable;

    @FXML
    private TableColumn<Simbols, String> notTerminalNameColumn;

    @FXML
    private Label terminalNameLabel;

    @FXML
    private TextField inputSequence;

    @FXML
    private ComboBox<String> comboVariable;
    
    @FXML
    private ComboBox<String> comboSimbols;
    
    @FXML
    private TextArea textGrammar;

    @FXML
    private TableColumn<Simbols, String> terminalNameColumn;

    @FXML
    private ComboBox<String> comboInitial;

    @FXML
    private Label notTerminalNameLabel;
    
    @FXML
    private TreeView<String> boxRules;
    
    // Reference to the main application.
    private MainTcomp mainTcomp;
        
    @FXML
    private TextArea boxGrammar;
    
    ArrayList listRulesGLC = new ArrayList();
    ArrayList listContextGLC = new ArrayList();        
    ArrayList listNotSimbols = new ArrayList();        
    ArrayList resultItens = new ArrayList();

        ArrayList arrRight = new ArrayList();

    String vContextGLC;
    String vContextGrammar;
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public SimbolsOverviewController() {
    }

    /**
     * Initializes the controller class. 
     * This method is automatically called after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the simbols table with the two columns.
        notTerminalNameColumn.setCellValueFactory(cellData -> cellData.getValue().notTerminalNameProperty());
        terminalNameColumn.setCellValueFactory(cellData -> cellData.getValue().terminalNameProperty());
        
        // Clear simbols details.
        showSimbolsDetails(null);
        // Listen for selection changes and show the simbols details when changed.
        simbolsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showSimbolsDetails(newValue));
        
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainTcomp
     */
    public void setMainTcomp(MainTcomp mainTcomp) {
        this.mainTcomp = mainTcomp;
        
        // Add observable list data to the table
        simbolsTable.setItems(mainTcomp.getSimbolsData());

    }
    
    /**
     * Fills all text fields to show details about the simbols.
     * If the specified simbols is null, all text fields are cleared.
     * 
     * @param simbols the simbols or null
     */
    private void showSimbolsDetails(Simbols simbols) {
        
        if (simbols != null) {
            // Fill the labels with info from the simbols object.
            notTerminalNameLabel.setText(simbols.getnotTerminalName());
            terminalNameLabel.setText(simbols.getTerminalName());
        } else {
            // Simbols is null, remove all the text.
            notTerminalNameLabel.setText("");
            terminalNameLabel.setText("");
        }
    }
    
    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteSimbols() {
        
        int selectedIndex = simbolsTable.getSelectionModel().getSelectedIndex();
        
        if (selectedIndex >= 0) {
            simbolsTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainTcomp.getPrimaryStage());
            alert.setTitle("Nenhuma seleção");
            alert.setHeaderText("Não há símbolos selecionados");
            alert.setContentText("Por favor, selecione uma linha na tabela.");
            
            alert.showAndWait();
        }
    }
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new simbols.
     */
    @FXML
    private void handleNewSimbols() {
        Simbols tempSimbols = new Simbols();
        boolean okClicked = mainTcomp.showSimbolsEditDialog(tempSimbols);
        if (okClicked) {
            mainTcomp.getSimbolsData().add(tempSimbols);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected simbols.
     */
    @FXML
    private void handleEditSimbols() {
        Simbols selectedSimbols = simbolsTable.getSelectionModel().getSelectedItem();
        if (selectedSimbols != null) {
            boolean okClicked = mainTcomp.showSimbolsEditDialog(selectedSimbols);
            if (okClicked) {
                showSimbolsDetails(selectedSimbols);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainTcomp.getPrimaryStage());
            alert.setTitle("Nenhuma seleção");
            alert.setHeaderText("Não há símbolos selecionados");
            alert.setContentText("Por favor, selecione uma linha na tabela.");
            
            alert.showAndWait();
        }
    }
    
//    @FXML
//    private void buttonSaveTransition() {
//    }

    
    @FXML
    private void handleExecuteSimbols(ActionEvent event) {
        
        ArrayList listSimbols = new ArrayList();
        ArrayList listInitial = new ArrayList();
	
        comboVariable.getItems().clear();
        comboInitial.getItems().clear();
        comboSimbols.getItems().clear();
        
        for (int i = 0; i < mainTcomp.getSimbolsData().size(); i++) {
            
            String valuesTerminal = mainTcomp.getSimbolsData().get(i).getTerminalName();
            String valuesNotTerminal = mainTcomp.getSimbolsData().get(i).getnotTerminalName();
            
            if (!listSimbols.contains(valuesNotTerminal)){  
                listSimbols.add(valuesNotTerminal);  
            }
            
            if(!listSimbols.contains(valuesTerminal)){
                listSimbols.add(valuesTerminal);  
            }
            
            if(!listInitial.contains(valuesNotTerminal) && Character.isUpperCase(valuesNotTerminal.charAt(0))){
               listInitial.add(valuesNotTerminal);
           }
	}
        
        comboSimbols.getItems().addAll(listSimbols);
        comboVariable.getItems().addAll(listInitial);
        comboInitial.getItems().addAll(listInitial);
        
        comboSimbols.getSelectionModel().selectFirst();
        comboVariable.getSelectionModel().selectFirst();
        comboInitial.getSelectionModel().selectFirst();
    }
    
    @FXML
    private void buttonAddGLC() {
        
        String listRight = "";
        String vRight = "";
        String vLeft = "";
        
        ArrayList arrVariable = new ArrayList();
        ArrayList arrSimbols = new ArrayList();
        arrVariable.clear();
        
        String listVariable = comboVariable.getSelectionModel().getSelectedItem();
        String listSimbols = comboSimbols.getSelectionModel().getSelectedItem();
        
        if(!arrVariable.contains(listVariable)){
            arrVariable.add(listVariable);
        }

        if(!arrSimbols.contains(listSimbols)){
            arrSimbols.add(listSimbols);
            listRulesGLC.addAll(arrSimbols);

            for (Object list : listRulesGLC) {
                vRight += "" + list.toString() + ",";
                listRight += "" + list.toString() + "";
                arrRight.add(listRight);
            }

            for (Object listV : arrVariable) {
                vLeft += "" + listV.toString() + "";
                
            }
            
            String lastStrRight = vRight.substring(0, vRight.length() - 1);
            
            vContextGLC = vLeft + " -> " + lastStrRight + ";";
                        
            boxGrammar.setText(vContextGLC);
        }
        
    }
    
    @FXML
    private void buttonSaveGLC() {
        
        ArrayList listItens = new ArrayList();
        String rules = "";
        
        TreeItem item = new TreeItem("Rules");
        
        listContextGLC.add(vContextGLC);
                
        for (int i = 0; i < listContextGLC.size(); i++) {
            
            TreeItem level = new TreeItem(listContextGLC.get(i).toString());
                
            if(level.getValue() == listContextGLC.get(i).toString()){
                
                item.getChildren().add(level);
                item.setExpanded(true);
                boxRules.setRoot(item);
                listItens.add(level.getValue());                
            }
        }
        
        boxGrammar.clear();
        listRulesGLC.clear();
        
        for (Object itens : listItens) {
            
            if(!resultItens.contains(itens)){
                resultItens.add(itens);
            }
        }
        
        for (int i = 0; i < resultItens.size(); i++) {
            rules += resultItens.get(i).toString()  + "\n";
        }
        
        vContextGrammar = "G = (V,Σ,R,S) \n";
        vContextGrammar += "V = " +  comboVariable.getItems().toString() + "\n";
        vContextGrammar += "Σ = " + arrRight.toString()  + "\n";
        vContextGrammar += "R = {"+ rules  +"}\n";
        vContextGrammar += "S = " + comboInitial.getItems().toString();
                
        textGrammar.setText(vContextGrammar);   
    }    
    
    @FXML
    private void buttonGenerate() {
        
        CYK cyk = new CYK(resultItens, inputSequence.getText());
        
        if (cyk.validateChain(inputSequence.getText())){
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            
            alert.initOwner(mainTcomp.getPrimaryStage());
            alert.setTitle("Validação");
            alert.setHeaderText("A cadeia digitada é VÁLIDA"); 

            ButtonType buttonTree = new ButtonType("Árvore Sintática");
            ButtonType buttonCancel = new ButtonType("Ok", ButtonData.CANCEL_CLOSE);
            
            alert.getButtonTypes().setAll(buttonTree, buttonCancel);
            
            Optional<ButtonType> result = alert.showAndWait();
            
             if (result.get() == buttonTree) {
                // ... user chose "Three"
            } else {
                // ... user chose CANCEL or closed the dialog
            }

        }else{
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            
            alert.initOwner(mainTcomp.getPrimaryStage());
            alert.setTitle("Validação");
            alert.setHeaderText("A cadeia digitada é INVÁLIDA");  
            alert.setContentText("Por favor, digite uma cadeia válida.");          
            alert.showAndWait();
        }
        
//        ArrayList listGrammar = new ArrayList();
//        ArrayList listTerminals = new ArrayList();
//        ArrayList listVariables = new ArrayList();
//        
//        int countList = resultItens.size();
//        
//        for (Object object: resultItens) {
//            
//            String[] variables = object.toString().split("->");
//                        
//            if(!listVariables.contains(variables[0])){
//                listVariables.add(variables[0]);
//            }
//
//            String terminals[] = variables[1].split("[|]");
//
//            for (String t : terminals) {
//                String term = t.replace(";", "");
//                listTerminals.add(term);
//            }            
//        }
    }   
    
}
