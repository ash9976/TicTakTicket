/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Domain.DAOFabrik;
import Hibernate.objecte.Benutzer;
import Hibernate.objecte.Bestellung;
import Hibernate.objecte.Karte;
import Hibernate.objecte.Kategorie;
import Hibernate.objecte.Kunde;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;

/**
 *
 * @author Iryna
 */
public class UseCaseControllerBestellungErstellen {

    private DataManager dataManager;
    public Set<Karte> bestellteKartenSet = new HashSet(0);

    public UseCaseControllerBestellungErstellen() {
    }

    public ArrayList<Karte> getFreieKartenNachKategorie(Kategorie kategorie) {
        int x = kategorie.getKategorieId();
        int y = KonstantKartenStatus.FREI.getKartenstatusId();

        String hql = "FROM Karte  WHERE KategorieID = '" + x
                + "' AND KartenstatusID = '" + y + "'";

        Query query = DAOFabrik.getInstance().getCurrentSession().createQuery(hql);

        List list = query.list();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return (ArrayList<Karte>) list;
    }

    public void legeKartenInWarenkorb(Kategorie kategorie, int anzahl) {
        int x = kategorie.getKategorieId();
        int y = KonstantKartenStatus.FREI.getKartenstatusId();

        String hql = "FROM Karte  WHERE KategorieID = '" + x + "'" + " AND KartenstatusID = '" + y + "'";
        Query query = DAOFabrik.getInstance().getCurrentSession().createQuery(hql).setMaxResults(anzahl);
        List list = query.list();
        if (list != null || list.size() != 0) {


            ArrayList<Karte> karten = (ArrayList<Karte>) query.list();
            for (Karte k : karten) {
                karteBlockieren(k);
                bestellteKartenSet.add(k);
            }
        }
    }

    // nur bei kauf ohne reservierung, für reservierung nur karteKaufen
    // gui macht  karteKaufen für jede karte und dann hier speichern
    public void verkaufSpeichern(Benutzer benutzer, Kunde kunde) throws Exception {
        if (bestellteKartenSet.isEmpty()) {
            throw new Exception("Keine Karten zum speichern");
        } else {
            Date datum = new Date();
            Iterator<Karte> iterator = bestellteKartenSet.iterator();
            while (iterator.hasNext()) {
                Karte b = iterator.next();
                this.karteKaufen(b, false);
            }

            Bestellung bestellung = new Bestellung(benutzer, kunde, datum, bestellteKartenSet);
            //dataManager.bestellungSpeichern(benutzer, kunde, datum, bestellteKartenSet);
            DAOFabrik.getInstance().getBestellungDAO().saveORupdate(bestellung);

        }
    }

    
    
    
    
    // Weitere Metoden für UseCase
    public void reservierungSpeichern(Benutzer benutzer, Kunde kunde) throws Exception {
        if (kunde == null) {
            throw new Exception("Kein kundenummer");
        }
        if (bestellteKartenSet.isEmpty()) {
            throw new Exception("Keine Karten zum speichern");
        } else {
            Date datum = new Date();
            //dataManager.bestellungSpeichern(benutzer, kunde, datum, bestellteKartenSet);
            DAOFabrik.getInstance().getBestellungDAO().
                    saveORupdate(new Bestellung(benutzer, kunde, datum, bestellteKartenSet));
        }
    }

    public BigDecimal calculateCost(double itemQuantity, BigDecimal itemPrice) {
        BigDecimal itemCost = itemPrice.multiply(new BigDecimal(itemQuantity));
        return itemCost;
    }

    public void karteKaufen(Karte karte, boolean istErmaessigt) {

        karte.setKartenstatus(KonstantKartenStatus.VERKAUFT);// dodelat
        karte.setErmaessigt(istErmaessigt);
        if (istErmaessigt) {
            int i = (100 - karte.getKategorie().getVeranstaltung().getErmaessigung());

            BigDecimal bd = new java.math.BigDecimal(String.valueOf(i));
            BigDecimal preis = calculateCost(i, karte.getKategorie().getPreis()).divide(new java.math.BigDecimal(String.valueOf(100)));

            karte.setPreis(preis);
        } else {
            karte.setPreis(karte.getKategorie().getPreis());
        }
        DAOFabrik.getInstance().getKarteDAO().saveORupdate(karte);
    }

    public void karteReservieren(Karte karte) {
        karte.setKartenstatus(KonstantKartenStatus.RESERVIERT);
    }

    public void karteBlockieren(Karte karte) {
        karte.setKartenstatus(KonstantKartenStatus.BLOKIERT);
        DAOFabrik.getInstance().getKarteDAO().saveORupdate(karte);
    }

    public void karteFreigeben(Karte karte) {
        karte.setKartenstatus(KonstantKartenStatus.FREI);
        DAOFabrik.getInstance().getKarteDAO().saveORupdate(karte);
    }

    public Bestellung reservierungSuchen(int id) {
        //Bestellung bestellung = dataManager.getReservierungNachID(id);
        return DAOFabrik.getInstance().getBestellungDAO().findById(id, false);
    }

    //  public ArrayList<Bestellung> reservierungenSuchen(Kunde kunde) {
    //ArrayList<Bestellung> reservierungen = dataManager.getReservierungenVonKunde(kunde);
    // return reservierungen;
    //}
    public ArrayList<Kunde> kundeSuchen(String nachname) {
        ArrayList<Kunde> kunden = dataManager.getKundeNachNachname(nachname);

        return kunden;
    }

    public void loeschenAusWarenkorb(Karte karte) {
        karteFreigeben(karte);
        bestellteKartenSet.remove(karte);
    }

    // gibt eine Liste von Karten von Kunde, die status RESERVIERT haben ---- RABOTAET
    public ArrayList<Karte> getReservierteKartenVonKunde(Kunde kunde) {
        ArrayList<Karte> reservierteKarten = new ArrayList<Karte>();
        Set<Bestellung> bestellungen = DAOFabrik.getInstance().getKundeDAO().findById(kunde.getKundenId(), false).getBestellungs();
        if (bestellungen == null || bestellungen.size() == 0) {
            return null;
        }
        Iterator<Bestellung> iterator = bestellungen.iterator();
        while (iterator.hasNext()) {

            Set<Karte> karten = iterator.next().getKartes();
            Iterator<Karte> iterator2 = karten.iterator();

            while (iterator2.hasNext()) {
                Karte k = iterator2.next();
                if (k.getKartenstatus().equals(KonstantKartenStatus.RESERVIERT)) {
                    reservierteKarten.add(k);
                }
            }
        }

        return reservierteKarten;
    }
}