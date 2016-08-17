/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glc.view;

import glc.MainTcomp;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

/**
 *
 * @author laryssamuniz
 */
public class RootLayoutController {
    // Reference to the main application
    private MainTcomp mainTcomp;

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainTcomp
     */
    public void setMainTcomp(MainTcomp mainTcomp) {
        this.mainTcomp = mainTcomp;
    }

    /**
     * Creates an empty address book.
     */
    @FXML
    private void handleNew() {
        mainTcomp.getSimbolsData().clear();
        mainTcomp.setSimbolsFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select an RC to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainTcomp.getPrimaryStage());

        if (file != null) {
            mainTcomp.loadSimbolsDataFromFile(file);
        }
    }

    /**
     * Saves the file to the simbols file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        File simbolsFile = mainTcomp.getSimbolsFilePath();
        if (simbolsFile != null) {
            mainTcomp.saveSimbolsDataToFile(simbolsFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainTcomp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainTcomp.saveSimbolsDataToFile(file);
        }
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
    	alert.setTitle("Reconhecedor de Cadeias");
    	alert.setHeaderText("About");
    	alert.setContentText("Author: Laryssa Muniz\nWebsite: https://github.com/laryssamuniz");

    	alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
