
package qara.younes.tprappeljava.controllers;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import qara.younes.tprappeljava.Main;
import qara.younes.tprappeljava.bean.Employe;
import qara.younes.tprappeljava.service.EmployeService;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


public class GestionController implements Initializable {


    @FXML
    public TextField matriculeId, prenomId, nomId, matriculeSearch, nomSearch;
    @FXML
    public DatePicker datePickerId;
    @FXML
    public RadioButton radioM, radioF;
    @FXML
    public ComboBox comboBoxId, specSearch;

    @FXML
    public TableView<Employe> tableView;
    @FXML
    public TableColumn<Employe, String> matriculeColumn, nomColumn, prenomColumn, specialiteColumn, date_embaucheColumn, sexeColumn;

    @FXML
    public Button search;

    @FXML
    public CheckBox matriculeCheck, nomCheck, specCheck;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        comboBoxId.getItems().addAll(
                "Java", "C++", "PHP", "Python", "JavaScript", "Ruby", "Swift", "Kotlin", "Go", "Rust",
                "TypeScript", "Perl", "C#", "Dart", "Scala", "R", "Lua", "MATLAB", "Haskell", "Groovy",
                "Elm", "Cobol", "Fortran", "Assembly", "Objective-C", "Erlang", "Lisp", "Prolog", "Scheme",
                "Ada", "VHDL", "COBOL", "PL/SQL", "SQL", "Bash", "Clojure", "F#", "Julia", "Crystal", "OCaml"
        );

        specSearch.getItems().addAll(
                "Java", "C++", "PHP", "Python", "JavaScript", "Ruby", "Swift", "Kotlin", "Go", "Rust",
                "TypeScript", "Perl", "C#", "Dart", "Scala", "R", "Lua", "MATLAB", "Haskell", "Groovy",
                "Elm", "Cobol", "Fortran", "Assembly", "Objective-C", "Erlang", "Lisp", "Prolog", "Scheme",
                "Ada", "VHDL", "COBOL", "PL/SQL", "SQL", "Bash", "Clojure", "F#", "Julia", "Crystal", "OCaml"
        );

        List<Employe> emp = EmployeService.findAll();
        displayEmployesInTable(emp);



        /* set text on the fields when I click on the table row*/
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Employe employe = tableView.getSelectionModel().getSelectedItem();

                matriculeId.setText(String.valueOf(employe.getMatricule()));
//                matriculeId.setDisable(true);
                prenomId.setText(employe.getPrenom());
                nomId.setText(employe.getNom());
                String specialitiesString = String.join(",", employe.getSpecialite().toString());
                comboBoxId.getSelectionModel().select(specialitiesString);

                LocalDate localDate = Instant.ofEpochMilli(employe.getDate_embauche().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                datePickerId.setValue(localDate);

                if (employe.getSexe() == 'F') {
                    radioF.setSelected(true);
                    radioM.setSelected(false);

                } else {
                    radioM.setSelected(true);
                    radioF.setSelected(false);
                }
            }
        });


        nomSearch.setDisable(true);
        nomSearch.editableProperty().bind(nomCheck.selectedProperty());
        nomCheck.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                nomSearch.setText("");
                nomSearch.setDisable(false);
            } else {
                nomSearch.setDisable(true);
            }
        });

        matriculeSearch.setDisable(true);
        matriculeSearch.editableProperty().bind(matriculeCheck.selectedProperty());
        matriculeCheck.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue) {
                matriculeSearch.setText("");
                matriculeSearch.setDisable(false);
            } else {
                matriculeSearch.setDisable(true);
            }
        }));


        specSearch.setDisable(true);
        specSearch.editableProperty().bind(specCheck.selectedProperty());
        specCheck.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                specSearch.setValue(null);
                specSearch.setDisable(false);
                specSearch.setValue("Java");
            } else {
                specSearch.setDisable(true);
            }
        });


    }

    public void displayEmployesInTable(List<Employe> emp) {

        /* display all the users in the table */


        matriculeColumn.setCellValueFactory(new PropertyValueFactory<Employe, String>("matricule"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<Employe, String>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<Employe, String>("prenom"));
        specialiteColumn.setCellValueFactory(new PropertyValueFactory<Employe, String>("specialite"));
        date_embaucheColumn.setCellValueFactory(new PropertyValueFactory<Employe, String>("date_embauche"));
        sexeColumn.setCellValueFactory(new PropertyValueFactory<Employe, String>("sexe"));



        /* this block of code is responsible for saving the list as a string*/
        specialiteColumn.setCellValueFactory(cellData -> {
            String specialites = cellData.getValue().getSpecialite();
            if (specialites == null || specialites.isEmpty()) {
                return new SimpleStringProperty("");
            } else {
                String specialitesString = String.join(", ", specialites);
                return new SimpleStringProperty(specialitesString);
            }
        });

        for (Employe e : emp) {
            tableView.getItems().add(e);
        }


    }


    public void ajouter() {
        if (matriculeId.getText() == null
                || nomId.getText().isEmpty()
                || prenomId.getText().isEmpty()
                || comboBoxId.getValue().toString().isEmpty()
                || datePickerId.getValue() == null
        ) {
            showErrorAlert("S'il vous plaît, remplissez tous les champs!");
            return;
        }

        int matricule = Integer.parseInt(matriculeId.getText());
        String nom = nomId.getText();
        String prenom = prenomId.getText();
        String listSpecialite = comboBoxId.getValue().toString();
        Instant instant = datePickerId.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date date_embauche = Date.from(instant);
        char gender = radioF.isSelected() ? 'F' : 'M';


        try {
            Employe newEmploye = new Employe(
                    matricule,
                    nom,
                    prenom,
                    listSpecialite,
                    date_embauche,
                    gender
            );
            EmployeService.create(newEmploye);
            showSuccessAlert("L'employé a été enregistré avec succès.!");
            tableView.getItems().clear();
            List<Employe> emp = EmployeService.findAll();
            displayEmployesInTable(emp);
        } catch (Exception e) {
            showExceptionAlert("Error", "L'opération d'ajout a échoué!", e);
        }


    }


    public void supprimer() {
        if(!showConfirmationAlert("suppression","are you sure?","select ok to continue")){
            return;
        }
        if (matriculeId.getText() == null
                || nomId.getText().isEmpty()
                || prenomId.getText().isEmpty()
                || comboBoxId.getValue().toString().isEmpty()
                || datePickerId.getValue() == null
        ) {
            showErrorAlert("S'il vous plaît, remplissez tous les champs!");
            return;
        }

        int matricule = Integer.parseInt(matriculeId.getText());
        String nom = nomId.getText();
        String prenom = prenomId.getText();
        String listSpecialite = comboBoxId.getValue().toString();
        Instant instant = datePickerId.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date date_embauche = Date.from(instant);
        char gender = radioF.isSelected() ? 'F' : 'M';

        try {
            Employe employeToDelete = new Employe(
                    matricule,
                    nom,
                    prenom,
                    listSpecialite,
                    date_embauche,
                    gender
            );
            boolean deleteResult = EmployeService.delete(employeToDelete);
            if (!deleteResult) {
                showErrorAlert("message");
                return;
            }
            showSuccessAlert("Employe Has Been Successfully Deleted");
            tableView.getItems().clear();
            matriculeId.clear();
            nomId.clear();
            prenomId.clear();
            comboBoxId.setValue("");
            List<Employe> emp = EmployeService.findAll();
            displayEmployesInTable(emp);
        } catch (Exception e) {
            showExceptionAlert("Error", "L'opération de suppression a échoué!", e);
        }
    }

    public void modifier() {
        if(!showConfirmationAlert("modification","are you sure?","select ok to continue")){
            return;
        }
        Employe newEmploye = null;
        char gender = radioF.isSelected() ? 'F' : 'M';
        Instant instant = datePickerId.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date date_embauche = Date.from(instant);
        try {
            newEmploye = new Employe(
                    Integer.parseInt(matriculeId.getText()),
                    nomId.getText(),
                    prenomId.getText(),
                    comboBoxId.getValue().toString(),
                    date_embauche,
                    gender
            );

            boolean updateStatus = EmployeService.update(newEmploye);
            if (!updateStatus) {
                showErrorAlert("Error updating the employee");
                return;
            }
            showSuccessAlert("Employe modifier avec succes");
            tableView.getItems().clear();
            List<Employe> emp = EmployeService.findAll();
            displayEmployesInTable(emp);
        } catch (Exception e) {
            showExceptionAlert("Exception", "something wen wrong", e);
        }
    }


    public void criteieaSearch() {
        List<Employe> listEmp = new ArrayList<>();

        String matricule = matriculeSearch.isDisabled() ? "" : matriculeSearch.getText();
        String specialite = specSearch.isDisabled() ? "" : specSearch.getValue().toString();
        String nom = nomSearch.isDisabled() ? "" : nomSearch.getText();

        boolean matriculeIsChecked = matriculeCheck.isSelected();
        boolean specialiteIsChecked = specCheck.isSelected();
        boolean nomIsChecked = nomCheck.isSelected();


        try {
            if (matriculeIsChecked && !specialiteIsChecked && !nomIsChecked) {
                listEmp = EmployeService.findByCritere(null, null, Integer.parseInt(matricule));
            } else if (specialiteIsChecked && !matriculeIsChecked && !nomIsChecked) {
                listEmp = EmployeService.findByCritere(specialite, null, 0);
            } else if (nomIsChecked && !matriculeIsChecked && !specialiteIsChecked) {
                listEmp = EmployeService.findByCritere(null, nom, 0);
            } else if (matriculeIsChecked
                    && nomIsChecked
                    && specialiteIsChecked) {
                listEmp = EmployeService.findByCritere(specialite, nom, Integer.parseInt(matricule));
            }


            if (!listEmp.isEmpty()) {
                showSuccessAlert("employees were found");
                tableView.getItems().clear();
                displayEmployesInTable(listEmp);

            } else {
                showErrorAlert("No employes were found");
            }

        } catch (Exception e) {
            showExceptionAlert("Exception accured", "something went wrong", e);
        }

    }
    /*
     * ALLERTS */

    public static void showSuccessAlert(String content) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION, content, ButtonType.OK);
            a.show();
        });
    }

    public static void showErrorAlert(String content) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.ERROR, content, ButtonType.OK);
            a.show();
        });
    }

    public static void showExceptionAlert(String headerText,
                                          String message, Throwable th) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        th.printStackTrace(pw);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(headerText);

        if (message != null) {
            alert.setContentText(message);
        } else {
            alert.setContentText(th.getMessage());
        }

        TextArea textArea = new TextArea(sw.toString());
        textArea.setEditable(false);
        alert.getDialogPane().setExpandableContent(textArea);
        alert.showAndWait();
    }


    public boolean showConfirmationAlert(String title, String headerText, String contentText) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(title);
        confirmationAlert.setHeaderText(headerText);
        confirmationAlert.setContentText(contentText);

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

        confirmationAlert.showAndWait();

        return confirmationAlert.getResult() == yesButton;
    }



    /*
    navigation
     */

    public void loadHomeView() {
        Parent root = null;
        try {

            root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("home-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage window = (Stage) (search.getScene().getWindow());
        Scene scene = new Scene(root);
        window.setScene(scene);
    }

    public void loadViewRecherche() {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("recherche-between-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage window = (Stage) (search.getScene().getWindow());
        window.setScene(new Scene(root));
    }

}


