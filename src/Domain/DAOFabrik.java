/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Domain;

/**
 *
 * Bürgi • Dietrich  • Federova  • Shabanova
 */
import Domain.DAOObjekte.DAOBenutzer;
import Domain.DAOObjekte.DAOBestellung;
import Domain.DAOObjekte.DAOKarte;
import Domain.DAOObjekte.DAOKartenstatus;
import Domain.DAOObjekte.DAOKategorie;
import Domain.DAOObjekte.DAOKuenstler;
import Domain.DAOObjekte.DAOKunde;
import Domain.DAOObjekte.DAOVeranstaltung;
import Domain.DAOObjekte.DAORolle;
import Domain.DAOObjekte.DAOVeranstaltungsort;
import Hibernate.konfiguration.HibernateUtil;

import org.hibernate.Session;

public class DAOFabrik {

    	private static DAOFabrik _instance;

	/**
	 * 
	 * @return instance.
	 */
	public static DAOFabrik getInstance() {
		if (_instance == null) {
			_instance = new DAOFabrik();
		}
		return _instance;
	}

	
	public DAOVeranstaltung getVeranstaltungDAO() {
		return (DAOVeranstaltung) instantiateDAO(DAOVeranstaltung.class);
	}
        
        public DAOBestellung getBestellungDAO() {
		return (DAOBestellung) instantiateDAO(DAOBestellung.class);
	}
        
        public DAOKartenstatus getKartenstatusDAO() {
		return (DAOKartenstatus) instantiateDAO(DAOKartenstatus.class);
	}
        
        public DAOKategorie getKategorieDAO() {
		return (DAOKategorie) instantiateDAO(DAOKategorie.class);
	}
        
        public DAOKarte getKarteDAO() {
		return (DAOKarte) instantiateDAO(DAOKarte.class);
	}
        
         public DAOKuenstler getKuenstlerDAO() {
		return (DAOKuenstler) instantiateDAO(DAOKuenstler.class);
	}
         
          public DAOVeranstaltungsort getVeranstaltungsDAO() {
		return (DAOVeranstaltungsort) instantiateDAO(DAOVeranstaltungsort.class);
	}
        
        public DAORolle getRolleDAO() {
		return (DAORolle) instantiateDAO(DAORolle.class);
	}
        
        public DAOKunde getKundeDAO() {
		return (DAOKunde) instantiateDAO(DAOKunde.class);
	}
        
        public DAOBenutzer getBenutzerDAO() {
		return (DAOBenutzer) instantiateDAO(DAOBenutzer.class);
	}
        
	/**
	 * Creates a new DAO
	 */
	private DAOGeneric<?, ?> instantiateDAO(Class<?> daoClass) {
		try {
			DAOGeneric<?, ?> dao = (DAOGeneric<?, ?>) daoClass.newInstance();
			dao.setSession(getCurrentSession());
			return dao;
		} catch (Exception ex) {
			throw new RuntimeException("Can not instantiate DAO: " + daoClass,
					ex);
		}
	}

	/**
	* @return the current session.
	 */
	
	public Session getCurrentSession() {
		return HibernateUtil.currentSession();
	}
}
    
    

