package com;

import java.io.IOException;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@ManagedBean
public class PredmetBean {
	private List<Predmet> predmeti;
	private String naslov, opis;
	private Date zacetek,konec;

    private Ucitelj ucitelj;
    private List<Ucenec> ucenci;


	
	private Long id;
	
	@Inject
	private Dao dao;
	
	@PostConstruct
	public void init(){
		predmeti = dao.getAllPredmeti();

	}
	
	public void setAllToBlank(){
		this.naslov = "";
		this.opis = "";
		this.zacetek= new Date();
		this.konec = new Date();
		
	}
	
	public void dodajPredmet(){
		Predmet pr = new Predmet();
		pr.naslov = this.naslov;
		pr.opis = this.opis;
		pr.zacetek = new java.sql.Date(this.zacetek.getTime());
		pr.konec = new java.sql.Date(this.konec.getTime());
		
		dao.createPredmet(pr);
		setAllToBlank();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("predmeti.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void izbrisiPredmet(Predmet pr){
		dao.deletePredmet(pr);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("predmeti.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void linkNaPrejsnjo(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("predmeti.xhtml");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	public String nastaviVrednosti(Predmet pr){
		this.naslov = pr.naslov;
		this.opis = pr.opis;
		this.zacetek = pr.zacetek;
		this.konec = pr.konec;
		this.id = pr.predmetId;

        this.ucitelj = pr.ucitelj;
        this.ucenci = new ArrayList<Ucenec>();
        this.ucenci.addAll(pr.ucenci);

		return "urediPredmet";
	}

    public void odstraniUcitelja(){
        this.ucitelj = null;

        Set tmp = new HashSet<Ucenec>(this.ucenci);
        Predmet uc = new Predmet(this.naslov, this.opis, new java.sql.Date(this.zacetek.getTime()), new java.sql.Date(this.konec.getTime()), this.ucitelj, tmp);
        uc.predmetId = this.id;

        dao.updatePredmet(uc);

    }
	
	public void urediPredmet(){
		Predmet pr = new Predmet();
		pr.opis = this.opis;
		pr.naslov = this.naslov;
		pr.zacetek = new java.sql.Date(this.zacetek.getTime());;
		pr.konec = new java.sql.Date(this.zacetek.getTime());
		pr.predmetId = this.id;
        Set tmp = new HashSet<Ucenec>(this.ucenci);
        pr.ucenci = tmp;
        pr.ucitelj = this.ucitelj;
		
		dao.updatePredmet(pr);
		setAllToBlank();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("predmeti.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void linkNaNaslednjo(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("ucitelji_ucenci.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public List<Predmet> getPredmeti() {
		return predmeti;
	}
	public void setPredmeti(List<Predmet> predmeti) {
		this.predmeti = predmeti;
	}
	public String getNaslov() {
		return naslov;
	}
	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public Date getZacetek() {
		return zacetek;
	}
	public void setZacetek(Date zacetek) {
		this.zacetek = zacetek;
	}
	public Date getKonec() {
		return konec;
	}
	public void setKonec(Date konec) {
		this.konec = konec;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

    public Ucitelj getUcitelj() {
        if(this.ucitelj == null){
            Ucitelj tmp = new Ucitelj();
            tmp.ime = "Trenutno predmet nima nosilca.";
            return tmp;
        }
        return ucitelj;
    }

    public void setUcitelj(Ucitelj ucitelj) {
        this.ucitelj = ucitelj;
    }

    public List<Ucenec> getUcenci() {
        return ucenci;
    }

    public void setUcenci(List<Ucenec> ucenci) {
        this.ucenci = ucenci;
    }

}
