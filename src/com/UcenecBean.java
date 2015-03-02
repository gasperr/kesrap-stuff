package com;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ManagedBean
public class UcenecBean {
	private List<Ucenec> ucenci;
	private String ime, priimek;
	private Long id;
    private List<Predmet> predmeti;
    private Date rojstniDatum;


    @Inject
	private Dao dao;



    @PostConstruct
	public void init(){
		ucenci = dao.getAllUcenci();
	}

    public void izpisiPredmete(){
        for(int i = 0; i < this.predmeti.size(); i++){
            System.out.println(predmeti.get(i).getNaslov());
        }
    }

    public void dodajUcenca(){
		if(!this.ime.equals("") && !this.priimek.equals("")){
			Ucenec uc = new Ucenec();
			uc.ime = this.ime;
			uc.priimek = this.priimek;
            uc.datumRojstva = new java.sql.Date(this.rojstniDatum.getTime());

           Set tmp = new HashSet<>(this.predmeti);

            uc.predmeti = tmp;


			dao.createUcenec(uc);

		}
		setAllToBlank();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("ucitelji_ucenci.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void izbrisiUcenca(Ucenec uc){

		dao.deleteUcenec(uc);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("ucitelji_ucenci.xhtml");
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
	public String nastaviVrednosti(Ucenec uc){
		this.ime = uc.ime;
		this.priimek = uc.priimek;
		this.id  = uc.ucenecId;
        this.predmeti.addAll(uc.getPredmeti());
        this.rojstniDatum = uc.datumRojstva;
		return "urediUcenca";
	}
	public void setAllToBlank(){
		this.ime = "";
		this.priimek = "";
	}
	public void urediUcenca(){
		Ucenec uc = new Ucenec();
		uc.ime = this.ime;
		uc.priimek = this.priimek;
		uc.ucenecId = this.id;
        uc.predmeti = new HashSet<>(this.predmeti);
        uc.datumRojstva = new java.sql.Date(this.rojstniDatum.getTime());


		dao.updateUcenec(uc);
		setAllToBlank();
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

	public List<Ucenec> getUcenci() {
		return ucenci;
	}

	public void setUcenci(List<Ucenec> ucenci) {
		this.ucenci = ucenci;
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

    public Date getRojstniDatum() {
        return rojstniDatum;
    }

    public void setRojstniDatum(Date rojsniDatum) {
        this.rojstniDatum = rojsniDatum;
    }
}
