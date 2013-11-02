/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import Domain.DAOFabrik;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import Hibernate.objecte.Bestellung;
import Hibernate.objecte.Karte;
import Hibernate.objecte.Kategorie;
import Hibernate.objecte.Kunde;
import Hibernate.objecte.Benutzer;
import Hibernate.objecte.Kuenstler;
import Hibernate.objecte.Veranstaltung;
import Hibernate.objecte.Veranstaltungstyp;
import Hibernate.objecte.Veranstaltungsort;

/**
 *
 * @author Iryna
 */
public class DataManager<T> {
    private Session session  = DAOFabrik.getInstance().getCurrentSession();
    
    public void bestellungSpeichern(Benutzer benutzer, Kunde kunde, Date datum, Set<Karte> bestellteKartenSet) {
        Bestellung bestellung = new Bestellung();
    }
//sucht Reservierung nach ID, ID Kunde kriegt telefonisch mittgeteilt---rabotaet
    public Bestellung getReservierungNachID(int id) {
        String hql = "FROM Bestellung b WHERE b.bestellungId = '" + id + "'";
        Query query = session.createQuery(hql);
        List list = query.list();
        if (list == null || list.size() == 0) {
            return null;
        }
        return (Bestellung) list.get(0);
    }
    
    // gibt Karten Status RESERVIERT --- rabotaet
    //public Kartenstatus getKartenStatus(KonstantKartenStatus kks){
      //  String hql = "FROM Kartenstatus WHERE Name = " + status;
        //Query query = session.createQuery(hql);
        //List list = query.list();
        //return (Kartenstatus) list.get(0);
    //}
    
    
    
public ArrayList<Karte> getFreieKartenNachKategorie(Kategorie kategorie) {
        int x = kategorie.getKategorieId();
        int y = KonstantKartenStatus.FREI.getKartenstatusId();

        String hql = "FROM Karte  WHERE KategorieID = '" + x
                + "' AND KartenstatusID = '" + y + "'";
        Query query = session.createQuery(hql);
        List list = query.list();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return (ArrayList<Karte>) list;
    }


 
    // gibt liste von Kunden nach Nachname  ---- RABOTAET
    public ArrayList<Kunde> getKundeNachNachname(String nachname) {
        
        String hql = "FROM Kunde k WHERE k.nachname = '" + nachname + "'";
        Query query = session.createQuery(hql);
        List list = query.list();
        if (list == null || list.size() == 0) {
            return null;
        }
       
        return (ArrayList<Kunde>) list;
    }
// gibt gewuenschte Anzahl von von Karten zum Verkauf, nach kategorie ------ RABOTAET
    public ArrayList<Karte> getKartenNachKategorieUndAnzahl(Kategorie kategorie, int anzahl) { //Eventuell Anzahl weglassen
        int x = kategorie.getKategorieId();
        
        String hql = "FROM Karte  WHERE KategorieID = '" + x + "'" +"and "; //Kartenstatus frei abfragen
        Query query = session.createQuery(hql).setMaxResults(anzahl);
        List list = query.list();
        if (list == null || list.size() == 0) {
            return null;
        }
        return (ArrayList<Karte>)list;
    }

   
    // gibt anzahl freie Plaetze in der Kategorie ----- rabotaet-- dodelat kartenSatus
    public int anzahlFreiePlatzeNachKategorie(Kategorie kategorie) {
        int x = kategorie.getKategorieId();
        int y = KonstantKartenStatus.FREI.getKartenstatusId();
       
        String hql = "FROM Karte  WHERE KategorieID = '" + x +
                "' AND KartenstatusID = '"+ y + "'";
        Query query = session.createQuery(hql);
        List list = query.list();
        if (list == null || list.isEmpty()) {
            return 0;
        }
        return (int)list.size();
    }
}
