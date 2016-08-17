/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glc.view;

import glc.model.Simbols;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author laryssamuniz
 */
public class SimbolsEditDialogController {

    @FXML
    private TextField notTerminalNameField;
    @FXML
    private TextField terminalNameField;

    private Stage dialogStage;
    private Simbols simbols;
    private boolean okClicked = false;
    
    /**
     * Initializes the controller class. 
     * This method is automatically called after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        
        // Set the dialog icon.
        this.dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
    }

    /**
     * Sets the simbols to be edited in the dialog.
     * 
     * @param simbols
     */
    public void setSimbols(Simbols simbols) {
        this.simbols = simbols;

        notTerminalNameField.setText(simbols.getnotTerminalName());
        terminalNameField.setText(simbols.getTerminalName());
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            simbols.setnotTerminalName(notTerminalNameField.getText());
            simbols.setTerminalName(terminalNameField.getText());
            
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (notTerminalNameField.getText() == null || notTerminalNameField.getText().length() == 0) {
            errorMessage += "Símbolo inválido!\n"; 
        }
        if (terminalNameField.getText() == null || terminalNameField.getText().length() == 0) {
            errorMessage += "Símbolo inválido!\n"; 
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Campos inválidos.");
            alert.setHeaderText("Corrija campos inválidos");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }   

}
