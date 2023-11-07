package qara.younes.tprappeljava.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import qara.younes.tprappeljava.Main;

import java.io.IOException;
import java.util.Objects;

public class HomeController {
    @FXML
    public Button currentNode;

    public void loadView() {
        Parent root = null;
        try {

            root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("gestion-emp-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage window = (Stage) (currentNode.getScene().getWindow());
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

        Stage window = (Stage) (currentNode.getScene().getWindow());
        window.setScene(new Scene(root));
    }


    public void exit(){
        Stage stage = (Stage) currentNode.getScene().getWindow();
        stage.close();
    }
}
