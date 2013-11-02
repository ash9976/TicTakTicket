package controller;

import Domain.DAOFabrik;
import Hibernate.objecte.Karte;
import Hibernate.objecte.Kuenstler;
import Hibernate.objecte.Kunde;
import Hibernate.objecte.Veranstaltung;
import Hibernate.objecte.Veranstaltungsort;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class TestSearch {
	

	public static void main(String[] args) {
            

            
            
            UseCaseControllerSearch ucsearch = new UseCaseControllerSearch();
            System.out.println("ALLE VERANSTALTUNGEN");
            for (Veranstaltung v: ucsearch.ver){
                 System.out.println(v.getName() +"  "+  v.getDatumUhrzeit()+"   " + v.getVeranstaltungsort().getAdresse());
                 Iterator<Kuenstler> iterator2 = v.getKuenstlers().iterator();
                 while (iterator2.hasNext()){
                     Kuenstler k = iterator2.next();
                 System.out.println(k.getName());
                 }
                 
            }
            System.out.println("FILTER");

            Veranstaltungsort ort = DAOFabrik.getInstance().getVeranstaltungsDAO().findById(2, false);
            System.out.println(ort.getAdresse());

            Date data = new Date();
            System.out.println("AB  " +data.toString());

            Kuenstler kun= DAOFabrik.getInstance().getKuenstlerDAO().findById(2, false);
            System.out.println("KUNSTLER  " + kun.getName());

            List<Veranstaltung> gesuchteVeranstaltungen  = ucsearch.searchFilter(ort, data, kun);
            System.out.println("RESULT");
            for (Veranstaltung v: gesuchteVeranstaltungen){
                 System.out.println(v.getName() + v.getDatumUhrzeit() + v.getVeranstaltungsort().getAdresse());
                 Iterator<Kuenstler> iterator2 = v.getKuenstlers().iterator();
                 while (iterator2.hasNext()){
                     Kuenstler k = iterator2.next();
                 System.out.println(k.getName());
                 }
            }
              

            
             
        }	 

}