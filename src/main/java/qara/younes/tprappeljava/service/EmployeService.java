package qara.younes.tprappeljava.service;


import qara.younes.tprappeljava.bean.Employe;
import qara.younes.tprappeljava.connexion.Connexion;
import qara.younes.tprappeljava.controllers.GestionController;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeService {

    private static final String STATEMENT = "INSERT INTO Employe (matricule, nom, prenom, specialite, date_embauche, sexe) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String STATEMENT_DELETE = "DELETE FROM Employe WHERE matricule = ? ";

    private static final String STATEMENT_UPDATE = "UPDATE Employe SET nom=?, prenom=?, specialite=?, date_embauche=?, sexe=? WHERE matricule = ?";

    private static final String STATEMENT_FIND = "SELECT * FROM employe WHERE matricule=?";

    private static final String STATEMENT_FIND_ALL = "SELECT * FROM employe";

    private static final String STATEMENT_FIND_CRITERE = "SELECT * FROM employe WHERE 1=1 ";

    private static final String STATEMENT_FIND_BETWEEN = "SELECT * FROM employe WHERE date_embauche BETWEEN ? AND ?";
    public static boolean create(Employe e) {
        Connection con = Connexion.getConnexion();
        String joinedList = String.join(",",e.getSpecialite());
        try {

            PreparedStatement preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1, e.getMatricule());
            preparedStatement.setString(2, e.getNom());
            preparedStatement.setString(3, e.getPrenom());
            preparedStatement.setString(4, joinedList);
            preparedStatement.setDate(5, new java.sql.Date(e.getDate_embauche().getTime()));
            preparedStatement.setString(6, String.valueOf(e.getSexe()));

            int row = preparedStatement.executeUpdate();

            return row > 0;
        } catch (SQLException err) {

            System.out.println(err);
            return false;
        }
    }


    public static boolean delete(Employe e) {


        Connection con = Connexion.getConnexion();

        try {
            PreparedStatement preparedStatement = con.prepareStatement(STATEMENT_DELETE);
            preparedStatement.setInt(1, e.getMatricule());

            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException err) {

            GestionController.showExceptionAlert("there is s problem SQL","ereuuur asa7bi",err);
            return false;
        }

    }

    public static boolean update(Employe e) {
        Connection con = Connexion.getConnexion();

        try {
            PreparedStatement preparedStatement = con.prepareStatement(STATEMENT_UPDATE);
            preparedStatement.setString(1, e.getNom());
            preparedStatement.setString(2, e.getPrenom());
            preparedStatement.setString(3, e.getSpecialite().toString());
            preparedStatement.setDate(4, new java.sql.Date(e.getDate_embauche().getTime()));
            preparedStatement.setString(5, String.valueOf(e.getSexe()));
            preparedStatement.setInt(6, e.getMatricule());

            int result = preparedStatement.executeUpdate();

            return result > 0;

        } catch (SQLException err) {
            System.out.println(err);
            return false;
        }


    }

    public static Employe findByMatricule(int matricule) {
        Connection con = Connexion.getConnexion();
        ResultSet result = null;
        Employe em = null;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(STATEMENT_FIND);
            preparedStatement.setInt(1, matricule);

            result = preparedStatement.executeQuery();

            if (result.next()) {
                em = new Employe(
                        result.getInt("matricule"),
                        result.getString("nom"),
                        result.getString("prenom"),
                        result.getString("specialite"),
                        result.getDate("date_embauche"),
                        result.getString("sexe").charAt(0)
                );
            }


        } catch (SQLException err) {
            System.out.println(err);
            System.out.println("SQLException: " + err.getMessage());
            System.out.println("SQLState: " + err.getSQLState());
            System.out.println("VendorError: " + err.getErrorCode());
        }


        return em;

    }

    public static List<Employe> findAll() {
        Connection con = Connexion.getConnexion();
        ResultSet result = null;
        List<Employe> listEm = new ArrayList<>();

        try {
            Statement statement = con.createStatement();
            result = statement.executeQuery(STATEMENT_FIND_ALL);

            while (result.next()) {
                Employe em = new Employe(
                        result.getInt("matricule"),
                        result.getString("nom"),
                        result.getString("prenom"),
                        result.getString("specialite"),
                        result.getDate("date_embauche"),
                        result.getString("sexe").charAt(0)
                );
                listEm.add(em);
            }
        } catch (SQLException err) {
            System.out.println(err);
        }

        return listEm;

    }

    public static List<Employe> findByCritere(String specialite, String nom, int matricule){
        /**
         * return the list of employes based on the crieteria selected
         * if the user selected matricule and/or nom and/or specialite
         *
         * */

        Connection con = Connexion.getConnexion();
        List<Employe> listEm = new ArrayList<>();
        ResultSet result = null;

        try{
            StringBuilder queryBuilder = new StringBuilder(STATEMENT_FIND_CRITERE);
            int i = 1;
            PreparedStatement preparedStatement = null;

            if(specialite!=null && !specialite.isEmpty()) queryBuilder.append("AND specialite = ? ");

            if(nom!=null && !nom.isEmpty()) queryBuilder.append("AND nom = ? ");

            if(matricule>0) queryBuilder.append("AND matricule = ? ");

            preparedStatement = con.prepareStatement(queryBuilder.toString());

            if(specialite!=null && !specialite.isEmpty()) {
                preparedStatement.setString(i,specialite);
                i++;
            }

            if (nom != null && !nom.isEmpty()) {
                preparedStatement.setString(i, nom);
                i++;
            }

            if (matricule > 0) {
                preparedStatement.setInt(i, matricule);
            }



            result = preparedStatement.executeQuery();


            while (result.next()) {
                Employe employe = new Employe(
                        result.getInt("matricule"),
                        result.getString("nom"),
                        result.getString("prenom"),
                        result.getString("specialite"),
                        result.getDate("date_embauche"),
                        result.getString("sexe").charAt(0)
                );
                listEm.add(employe);
            }
        }catch(SQLException err){
            System.out.println(err);
        }

        return listEm;

    }

    public static List<Employe> findBetweenDate(Date d1,Date d2){
        Connection con = Connexion.getConnexion();
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        Employe emp = null;
        List<Employe> listEmp = new ArrayList<>();
        try{
            preparedStatement = con.prepareStatement(STATEMENT_FIND_BETWEEN);

            if(d1.getTime() < d2.getTime()){
                preparedStatement.setDate(1,d1);
                preparedStatement.setDate(2,d2);
            }else{
                preparedStatement.setDate(1,d2);
                preparedStatement.setDate(2,d1);
            }

            result = preparedStatement.executeQuery();


            while(result.next()){
                emp = new Employe(
                        result.getInt("matricule"),
                        result.getString("nom"),
                        result.getString("prenom"),
                        result.getString("specialite"),
                        result.getDate("date_embauche"),
                        result.getString("sexe").charAt(0)
                );

                listEmp.add(emp);

            }

        }catch(SQLException err){
            System.out.println(err);
        }

        return listEmp;
    }

}
