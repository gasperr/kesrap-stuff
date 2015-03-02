package com;


import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class Dao {
	@PersistenceContext(unitName="SolaAppMaven_v2")
	
	private EntityManager entityManager;
	
	//PREDMETI
	
	public void createPredmet(Predmet predmet){
		entityManager.persist(predmet);
	}
	public void deletePredmet(Predmet predmet){
		predmet = entityManager.find(Predmet.class, predmet.getPredmetId());
		entityManager.remove(predmet);
	} 
	public void updatePredmet(Predmet predmet){
		Predmet uc = entityManager.find(Predmet.class, predmet.predmetId);
		uc.naslov = predmet.naslov;
		uc.opis = predmet.opis;
		uc.zacetek = predmet.zacetek;
		uc.konec = predmet.konec;
        uc.ucitelj = predmet.ucitelj;
        uc.ucenci = predmet.ucenci;
		entityManager.merge(uc);
	}
	public List<Predmet> getAllPredmeti(){
		List<Predmet> predmetList = null;
		predmetList = entityManager.createQuery("SELECT g FROM Predmet g", Predmet.class).getResultList();
        return predmetList;
	}

    public Predmet getPredmetById(Long id){
        Predmet n = entityManager.find(Predmet.class, id);
        //System.out.println("Tole vrne entity manager: "+n.opis);
        return n;
    }

    public List<Predmet> getProstiPredmeti(Ucitelj uc){
        List<Predmet> pr = entityManager.createQuery("select g from Predmet g where uciteljId=NULL or uciteljId="+uc.uciteljId).getResultList();
        return pr;
    }

    public List<Predmet> getPredmeteUcitelja(Long id){
        List<Predmet> pr = entityManager.createQuery("select g from Predmet g where uciteljId="+id).getResultList();
        return pr;
    }

    public void izbrisiPredmetuUcitelja(Set<Predmet> predmeti){
        Iterator iter = predmeti.iterator();
        while(iter.hasNext()){
            Predmet pre = entityManager.find(Predmet.class, ((Predmet)iter.next()).predmetId);
            pre.ucitelj = null;
            entityManager.merge(pre);
        }

    }

	
	// UCITELJI
	
	public void createUcitelj(Ucitelj ucitelj, List<Predmet> predmeti){
		entityManager.persist(ucitelj);

        for(int i = 0; i < predmeti.size(); i++){
            Predmet p = entityManager.find(Predmet.class, predmeti.get(i).predmetId);
            p.ucitelj = ucitelj;                                                            //BI LAHKO TO RESILI Z CASCADNIM UPDEJTANJEM ?????
            entityManager.merge(p);
        }

	}
	public void deleteUcitelj(Ucitelj ucitelj){
		ucitelj = entityManager.find(Ucitelj.class, ucitelj.getUciteljId());
		entityManager.remove(ucitelj);
	} 
	public void updateUcitelj(Ucitelj ucitelj, List<Predmet> predmeti){
		Ucitelj uc = entityManager.find(Ucitelj.class, ucitelj.uciteljId);
		uc.ime = ucitelj.ime;
		uc.priimek = ucitelj.priimek;
        uc.predmeti = ucitelj.predmeti;
        uc.ucenci = ucitelj.ucenci;
		entityManager.merge(uc);

        for(int i = 0; i < predmeti.size(); i++){
            Predmet p = entityManager.find(Predmet.class, predmeti.get(i).predmetId);
            p.ucitelj = uc;                                                            //BI LAHKO TO RESILI Z CASCADNIM UPDEJTANJEM ?????
            entityManager.merge(p);
        }
	}



	public List<Ucitelj> getAllUcitelj(){
		List<Ucitelj> uciteljList = null;
		uciteljList = entityManager.createQuery("SELECT g FROM Ucitelj g", Ucitelj.class).getResultList();
        return uciteljList;
	}

    public Ucitelj getUciteljById(Long id){
        Ucitelj n = entityManager.find(Ucitelj.class, id);
        //System.out.println("Tole vrne entity manager: "+n.opis);
        return n;
    }


    //ne rabis!
    public Ucitelj getUciteljPredmeta(Predmet pr){
        Ucitelj uc = null;
        uc = entityManager.createQuery("select uc from Ucitelj uc join uc.predmeti pr where pr.predmetId="+pr.predmetId, Ucitelj.class).getSingleResult();
        return uc;
    }
	
	
	//UCENCI


    public Ucenec getUcenecById(Long id){
        Ucenec n = entityManager.find(Ucenec.class, id);
        //System.out.println("Tole vrne entity manager: "+n.opis);
        return n;
    }

	
	public void createUcenec(Ucenec ucenec){
		entityManager.persist(ucenec);
	}
	public void deleteUcenec(Ucenec ucenec){
		ucenec = entityManager.find(Ucenec.class, ucenec.getUcenecId());
		entityManager.remove(ucenec);
	} 
	public void updateUcenec(Ucenec ucenec){
		Ucenec uc = entityManager.find(Ucenec.class, ucenec.ucenecId);
		uc.ime = ucenec.ime;
		uc.priimek = ucenec.priimek;
		uc.ucenecId = ucenec.ucenecId;
        uc.predmeti = ucenec.predmeti;
        uc.datumRojstva = ucenec.datumRojstva;
		entityManager.merge(uc);
		
	}
	public List<Ucenec> getAllUcenci(){
		List<Ucenec> ucenecList = null;
		ucenecList = entityManager.createQuery("SELECT g FROM Ucenec g", Ucenec.class).getResultList();
        return ucenecList;
	}

    public List<Ucenec> getUcenecByPredmet(Long predmetId){
        List<Ucenec> listUcenec = null;
        listUcenec = entityManager.createQuery("select uc from Ucenec uc JOIN uc.predmeti pr where pr.predmetId="+predmetId, Ucenec.class).getResultList();

        return listUcenec;

    }

    public List<Ucitelj> getUciteljeUcenca(Long ucenecId){
        List<Ucitelj> listUcitelj = null;
        listUcitelj = entityManager.createQuery("select uc from Ucitelj uc JOIN uc.ucenci uce where uce.ucenecId="+ucenecId, Ucitelj.class).getResultList();

        return listUcitelj;
    }
}
