package com;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Predmet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	protected Long predmetId;
	
	protected String naslov, opis;
	Date zacetek, konec;
	
	@Version
	protected int version;
	
	@ManyToOne(targetEntity= Ucitelj.class, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name="uciteljId")
	protected Ucitelj ucitelj;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="ucenec_predmet",
            joinColumns={
                    @JoinColumn(name="PredmetID", referencedColumnName = "predmetId")},
            inverseJoinColumns={
                    @JoinColumn(name="UcenecID", referencedColumnName = "ucenecId")}
    )
	protected Set<Ucenec> ucenci;



    public Predmet() {
    }

    public Predmet(String naslov, String opis, Date zacetek, Date konec, Ucitelj ucitelj, Set<Ucenec> ucenci) {

        this.naslov = naslov;
        this.opis = opis;
        this.zacetek = zacetek;
        this.konec = konec;
        this.ucitelj = ucitelj;
        this.ucenci = ucenci;
    }

    public Long getPredmetId() {
		return predmetId;
	}
	public void setPredmetId(Long predmetId) {
		this.predmetId = predmetId;
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
	public Ucitelj getUcitelj() {
		return ucitelj;
	}
	public void setUcitelj(Ucitelj ucitelj) {
		this.ucitelj = ucitelj;
	}
	public Set<Ucenec> getUcenci() {
		return ucenci;
	}
	public void setUcenci(Set<Ucenec> ucenci) {
		this.ucenci = ucenci;
	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Predmet)) return false;

        Predmet predmet = (Predmet) o;

        if (!konec.equals(predmet.konec)) return false;
        if (!naslov.equals(predmet.naslov)) return false;
        if (!opis.equals(predmet.opis)) return false;
        if (!zacetek.equals(predmet.zacetek)) return false;

        return true;
    }

}
