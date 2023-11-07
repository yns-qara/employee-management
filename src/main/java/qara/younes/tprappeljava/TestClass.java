package qara.younes.tprappeljava;

import qara.younes.tprappeljava.bean.Employe;
import qara.younes.tprappeljava.service.EmployeService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestClass {

    public static void main(String[] args) {

        int matricule = 11111;
        String nom = "Doe";
        String prenom = "John";

        List<String> specialites = new ArrayList<>();
        specialites.add("test");
        specialites.add("test");

        String specialiteC = "Cobol";
        Date dateEmbauche = null;
        try {
            dateEmbauche = new SimpleDateFormat("dd/MM/yyyy").parse("15/10/2023");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        char sexe = 'M';

//        employe 2

        String nom2 = "younes";
        String prenom2 = "qara";

        List<String> specialites2 = new ArrayList<>();
        specialites2.add("test1");
        specialites2.add("test2");

        Date dateEmbauche2 = null;
        try {
            dateEmbauche2 = new SimpleDateFormat("dd/MM/yyyy").parse("15/10/2003");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        char sexe2 = 'M';



        Employe employee = new Employe(matricule, nom, prenom, specialiteC, dateEmbauche, sexe);
//        Employe employee2 = new Employe(matricule,nom2,prenom2,specialites2,dateEmbauche2,sexe2);

//        boolean status = EmployeService.create(employee);
//        System.out.println(status);

//        System.out.println(specialites);

//        boolean deleted = EmployeService.delete(employee);
//        System.out.println(deleted);

//        boolean updated = EmployeService.update(employee2);
//        System.out.println(updated);


//        Employe newE = EmployeService.findByMatricule(12345);
//        System.out.println(newE.getNom());

//        List<Employe> listEm = new ArrayList<>();
//
//        listEm = EmployeService.findAll();
//
//        for(Employe em : listEm){
//            System.out.println(em.getMatricule());
//            System.out.println(em.getNom());
//            System.out.println(em.getPrenom());
//            System.out.println(em.getSpecialite());
//            System.out.println(em.getDate_embauche());
//            System.out.println(em.getSexe());
//        }


        List<Employe> ltest = EmployeService.findByCritere("Python",null,0);


        for(Employe e: ltest){
            System.out.println(e.getMatricule());
            System.out.println(e.getNom());
            System.out.println(e.getPrenom());
            System.out.println(e.getSpecialite());
            System.out.println(e.getDate_embauche());
            System.out.println(e.getSexe());
        }

        java.sql.Date startDate = java.sql.Date.valueOf("2022-01-01");
        java.sql.Date endDate = java.sql.Date.valueOf("2023-12-31");

        List<Employe> emp = EmployeService.findBetweenDate(startDate, endDate);


        for (Employe e : emp) {
            System.out.println(e.getMatricule());
            System.out.println(e.getNom());
            System.out.println(e.getPrenom());
            System.out.println(e.getSpecialite());
            System.out.println(e.getDate_embauche());
            System.out.println(e.getSexe());
        }



    }
}
