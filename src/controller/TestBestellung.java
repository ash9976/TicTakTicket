/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Domain.DAOFabrik;
import Hibernate.objecte.Benutzer;
import Hibernate.objecte.Karte;
import Hibernate.objecte.Kategorie;
import Hibernate.objecte.Kunde;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Bürgi • Dietrich • Fedorova • Shabanova
 */
public class TestBestellung {

    public static void main(String[] args) {



        UseCaseControllerBestellungErstellen uc = new UseCaseControllerBestellungErstellen();

        System.out.println("ALLE KARTEN VON KATEGORIE");
        Kategorie k = DAOFabrik.getInstance().getKategorieDAO().findById(1, false);
        Set<Karte> karten1 = k.getKartes();

        for (Karte kar : karten1) {
            System.out.println(kar.getName());
        }

        System.out.println("ALLE VERFÜGBARE KARTEN VON KATEGORIE");
        
        ArrayList<Karte> karten = uc.getFreieKartenNachKategorie(k);
        for (Karte kar : karten) {
            System.out.println(kar.getName());
        }

        System.out.println("LEGE KARTEN IN WAREN KORB");
        uc.legeKartenInWarenkorb(k, 1);
        Karte  reservierteKarte=  DAOFabrik.getInstance().getKarteDAO().findById(1, false);
        System.out.println(reservierteKarte.getName()+ " " + reservierteKarte.getKartenstatus().getName());

        System.out.println(uc.bestellteKartenSet.size());
        System.out.println("ALLE VERFÜGBARE KARTEN VON KATEGORIE");
        Kategorie k2 = DAOFabrik.getInstance().getKategorieDAO().findById(1, false);
        ArrayList<Karte> karten2 = uc.getFreieKartenNachKategorie(k2);
        for (Karte kar : karten2) {
            System.out.println(kar.getName());
        }


        Kunde kunde = DAOFabrik.getInstance().getKundeDAO().findById(2, false);
        Benutzer b = DAOFabrik.getInstance().getBenutzerDAO().findById(1, false);

        try {
            uc.verkaufSpeichern(b, kunde);
        } catch (Exception ex) {
            Logger.getLogger(TestBestellung.class.getName()).log(Level.SEVERE, null, ex);
        }
System.out.println("GEKAUFE KARTE");
        Karte  gekaufteKarte=  DAOFabrik.getInstance().getKarteDAO().findById(1, false);
System.out.println(gekaufteKarte.getName()+ " " +gekaufteKarte.getKartenstatus().getName());

    }
}
