/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glc;

import glc.model.Simbols;
import glc.model.SimbolsListWrapper;
import glc.view.RootLayoutController;
import glc.view.SimbolsEditDialogController;
import glc.view.SimbolsOverviewController;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author laryssamuniz
 */
public class MainTcomp extends Application {
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    /**
     * The data as an observable list of Simbols.
     */
    private final ObservableList<Simbols> simbolsData =
        FXCollections.observableArrayList(
            new Simbols("ADJ_AT", "the"),
            new Simbols("ADJ_AT", "a"),
            new Simbols("ADJ_AT", "no"),
            new Simbols("ADJ_AT", "pt_adj_at"),
            new Simbols("S", "ab"),
            new Simbols("S", "aaB")
        ); 
    
    /**
     * Returns the data as an observable list of Simbolss. 
     * @return
     */
    public ObservableList<Simbols> getSimbolsData() {
        return simbolsData;
    }
      
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Reconhecedor de Cadeias");
        
        // Set the application icon.
        this.primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
        
        initRootLayout();

        showSimbolsOverview();
    }

    /**
     * Initializes the root layout and tries to load the last opened
     * simbols file.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainTcomp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainTcomp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened simbols file.
        File file = getSimbolsFilePath();
        if (file != null) {
            loadSimbolsDataFromFile(file);
        }
    }

    /**
     * Shows the simbols overview inside the root layout.
     */
    public void showSimbolsOverview() {
        try {
            // Load simbols overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainTcomp.class.getResource("view/SimbolsOverview.fxml"));
            AnchorPane simbolsOverview = (AnchorPane) loader.load();

            // Set simbols overview into the center of root layout.
            rootLayout.setCenter(simbolsOverview);

            // Give the controller access to the main app.
            SimbolsOverviewController controller = loader.getController();
            controller.setMainTcomp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Opens a dialog to edit details for the specified simbols. 
     * If the user clicks OK, the changes are saved into the provided simbols object and true is returned.
     * 
     * @param simbols the simbols object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showSimbolsEditDialog(Simbols simbols) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainTcomp.class.getResource("view/SimbolsEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Simbols");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the simbols into the controller.
            SimbolsEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSimbols(simbols);
            
            // Set the dialog icon.
            dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    /**
     * Returns the simbols file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     * 
     * @return
     */
    public File getSimbolsFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainTcomp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     * 
     * @param file the file or null to remove the path
     */
    public void setSimbolsFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainTcomp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("Reconhecedor de Cadeias - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("Reconhecedor de Cadeias");
        }
    }
    
    /**
     * Loads simbols data from the specified file. The current simbols data will
     * be replaced.
     * 
     * @param file
     */
    public void loadSimbolsDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(SimbolsListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            SimbolsListWrapper wrapper = (SimbolsListWrapper) um.unmarshal(file);

            simbolsData.clear();
            simbolsData.addAll(wrapper.getSimbols());

            // Save the file path to the registry.
            setSimbolsFilePath(file);

        } catch (Exception e) { // catches ANY exception
        	Alert alert = new Alert(Alert.AlertType.ERROR);
        	alert.setTitle("Erro");
        	alert.setHeaderText("Não foi possível carregar os dados");
        	alert.setContentText("Não foi possível carregar dados do arquivo:\n" + file.getPath());
        	
        	alert.showAndWait();
        }
    }

    /**
     * Saves the current simbols data to the specified file.
     * 
     * @param file
     */
    public void saveSimbolsDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(SimbolsListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our simbols data.
            SimbolsListWrapper wrapper = new SimbolsListWrapper();
            wrapper.setSimbols(simbolsData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setSimbolsFilePath(file);
        } catch (Exception e) { // catches ANY exception
        	Alert alert = new Alert(Alert.AlertType.ERROR);
        	alert.setTitle("Erro");
        	alert.setHeaderText("Não foi possível carregar os dados");
        	alert.setContentText("Não foi possível carregar dados do arquivo:\n" + file.getPath());
        	
        	alert.showAndWait();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
