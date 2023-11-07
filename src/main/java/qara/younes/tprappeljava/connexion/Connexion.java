package qara.younes.tprappeljava.connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/Hr?useSSL=false";
    private static final String SERVEUR_SGBG = "localhost";
    private static final int PORT = 3306;
    private static final String DATABASE = "Rh";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "younesqara";


    public static Connection getConnexion(){
        Connection connexion = null;
        try{
            connexion = DriverManager.getConnection(DATABASE_URL,LOGIN,PASSWORD);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return connexion;
    }
}
