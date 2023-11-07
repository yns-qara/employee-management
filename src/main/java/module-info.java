module qara.younes.tprappeljava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens qara.younes.tprappeljava to javafx.fxml;
    exports qara.younes.tprappeljava;
    exports  qara.younes.tprappeljava.service;
    exports qara.younes.tprappeljava.controllers;
    exports qara.younes.tprappeljava.bean;
}