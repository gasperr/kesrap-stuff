package com;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.util.*;


@ManagedBean(name="uciteljBean")
public class UciteljBean {
	private List<Ucitelj> ucitelj;
	private String ime, priimek;
	private Long id;
    private List<Predmet> predmeti;
    private List<Ucenec> ucenci;

    private boolean dodajZUcenci;

    private List<Predmet> prostiPredmeti;


    @Inject
	private Dao dao;   
	
	@PostConstruct
	public void init(){
		ucitelj = dao.getAllUcitelj();
	}


	public void setAllToBlank(){
		this.ime = "";
		this.priimek = "";
	}
	
	public void dodajUcitelja(){
		if(!this.ime.equals("") && !this.priimek.equals("")){
			Ucitelj uc = new Ucitelj();
			uc.ime = this.ime;
			uc.priimek = this.priimek;

            Set tmp = new HashSet<>(this.predmeti);
            uc.predmeti = tmp;

			dao.createUcitelj(uc, this.predmeti);
		}
		setAllToBlank();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("ucitelji_ucenci.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void dodajUciteljaZUcenci(){
        if(!this.ime.equals("") && !this.priimek.equals("")){
            Ucitelj uc = new Ucitelj();
            uc.ime = this.ime;
            uc.priimek = this.priimek;

            Set tmp = new HashSet<>(this.predmeti);
            uc.predmeti = tmp;

            Set ucenci1 = new HashSet<Ucenec>();
            Set ids = new HashSet<Long>();
            for(int i = 0; i < this.predmeti.size(); i++){
                List<Ucenec> t = dao.getUcenecByPredmet(this.predmeti.get(i).predmetId);
                for (int j = 0; j < t.size(); j++){
                    if(!ids.contains(t.get(j).ucenecId)){
                        ucenci1.add(t.get(j));
                        ids.add(t.get(j).ucenecId);
                    }

                }

            }
            Iterator e = ids.iterator();
            while(e.hasNext()){
                System.out.println((e.next()));
            }
            uc.ucenci = ucenci1;

            dao.createUcitelj(uc, this.predmeti);
        }
        setAllToBlank();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ucitelji_ucenci.xhtml");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	public void izbrisiUcitelja(Ucitelj uc){
        //najprej poskrbi za FKje v predmetih
        Set tmp = uc.predmeti;
        Iterator i = tmp.iterator();
        while(i.hasNext()){
            Predmet pr = (Predmet)i.next();
            pr.ucitelj = null;
            dao.updatePredmet(pr);

        }

		dao.deleteUcitelj(uc);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("ucitelji_ucenci.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String nastaviVrednosti(Ucitelj uc){
		this.ime = uc.ime;
		this.priimek = uc.priimek;
		this.id = uc.uciteljId;
        this.predmeti.addAll(uc.predmeti);
        this.dodajZUcenci = true;



        Iterator e = uc.ucenci.iterator();
        while(e.hasNext()){
            System.out.println(((Ucenec)e.next()).ime);
        }


        this.ucenci = new ArrayList<Ucenec>();
        this.ucenci.addAll(uc.ucenci);

        Iterator<Ucenec> iter = this.ucenci.iterator();
        while(iter.hasNext()){
            if(iter.next().predmeti.retainAll(this.predmeti));

        }

		return "urediUcitelja";
	}
	public void urediUcitelja(){
		Ucitelj uc = new Ucitelj();
		uc.ime = this.ime;
		uc.priimek = this.priimek;
		uc.uciteljId = this.id;
        uc.predmeti = new HashSet<>(this.predmeti);

        if(dodajZUcenci == true){

            Set ucenci1 = new HashSet<>();
            Set ids = new HashSet<>();
            for(int i = 0; i < this.predmeti.size(); i++){
                List<Ucenec> t = dao.getUcenecByPredmet(this.predmeti.get(i).predmetId);
                for (int j = 0; j < t.size(); j++){
                    if(!ids.contains(t.get(j).ucenecId)){
                        ucenci1.add(t.get(j));
                        ids.add(t.get(j).ucenecId);
                    }

                }

            }

            uc.ucenci = ucenci1;
        }

        /*
        dobi vse predmete ucitelja
        izbrises tiste, ki so v this.predmeti, tako dobiš le tiste, ki jih hočeš odstanit ucitelju
         */

        Set<Predmet> predmetiUcitelja = new HashSet<>(dao.getPredmeteUcitelja(this.id));
        predmetiUcitelja.removeAll(this.predmeti);

        /*
        v predmeti ucitelja morajo biti sedaj vsi predmeti, ki so bili prej v ucitelju sedaj pa so bili odstranjeni
        tem predmetom je treba dat uciteljId na null
         */

        dao.izbrisiPredmetuUcitelja(predmetiUcitelja);



		dao.updateUcitelj(uc, this.predmeti);
		setAllToBlank();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ucitelji_ucenci.xhtml");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

    public void odstraniPredmetUcitelju(Predmet pr){
        //v predmetu moras odstranit id od ucitelja
        System.out.println("hahahahahahaha: u no sleep"+pr.naslov);
        // dao.odstraniPredmetUcitelju(pr);

    }

    public void test(){
        System.out.println("hahahahahahaha: u no sleep");
    }

	public List<Ucitelj> getUcitelj() {
		return ucitelj;
	}

	public void setUcitelj(List<Ucitelj> ucitelj) {
		this.ucitelj = ucitelj;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPriimek() {
		return priimek;
	}

	public void setPriimek(String priimek) {
		this.priimek = priimek;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

    public List<Predmet> getPredmeti() {
        return predmeti;
    }

    public void setPredmeti(List<Predmet> predmeti) {
        this.predmeti = predmeti;
    }

    public List<Ucenec> getUcenci() {
        return ucenci;
    }

    public void setUcenci(List<Ucenec> ucenci) {
        this.ucenci = ucenci;
    }

    public boolean isDodajZUcenci() {
        return dodajZUcenci;
    }

    public void setDodajZUcenci(boolean dodajZUcenci) {
        this.dodajZUcenci = dodajZUcenci;
    }

    public List<Predmet> getProstiPredmeti() {
        return prostiPredmeti;
    }

    public void setProstiPredmeti(List<Predmet> prostiPredmeti) {
        this.prostiPredmeti = prostiPredmeti;
    }
}
