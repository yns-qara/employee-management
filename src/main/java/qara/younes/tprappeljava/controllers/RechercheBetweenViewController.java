package qara.younes.tprappeljava.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class RechercheBetweenViewController implements Initializable {

    @FXML
    public DatePicker debutDate,finDate;
    @FXML
    public Button rechercher;

    @FXML
    public TableView tableView;
    @FXML
    public TableColumn<Employe, String> matriculeColumn, nomColumn, prenomColumn, specialiteColumn, date_embaucheColumn, sexeColumn;
    @FXML
    public Label total;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Employe> emp = EmployeService.findAll();
        displayEmployesInTable(emp);
        total.setText("Total : "+ emp.size());


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


    public void searchBetween(){
        List<Employe> emp = null;
        try{

            Date before = Date.valueOf(debutDate.getValue());
            Date after = Date.valueOf(finDate.getValue());

            emp =  EmployeService.findBetweenDate(before,after);
            tableView.getItems().clear();
            displayEmployesInTable(emp);
            total.setText("Total : "+ emp.size());

        }catch(Exception e){
            System.out.println(e);
        }
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
        Stage window = (Stage) (rechercher.getScene().getWindow());
        Scene scene = new Scene(root);
        window.setScene(scene);
    }

    public void loadGestioView() {
        Parent root = null;
        try {

            root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("gestion-emp-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage window = (Stage) (rechercher.getScene().getWindow());
        Scene scene = new Scene(root);
        window.setScene(scene);
    }




}
