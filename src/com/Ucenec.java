package com;


import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;


@Entity

public class Ucenec implements Serializable{
	private static final long serialVersionUID = 1L;
	 
	@Id 
	@GeneratedValue
	protected Long ucenecId;
	
	protected String ime;
	protected String priimek;
	protected Date datumRojstva;


	
	@Version
	protected int version;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="ucitelj_ucenec",
            joinColumns={
                    @JoinColumn(name="UcenecID")},
            inverseJoinColumns={
                    @JoinColumn(name="UciteljID")}
    )
	protected Set<Ucitelj> ucitelji;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="ucenec_predmet",
            joinColumns={
                    @JoinColumn(name="UcenecID", referencedColumnName = "ucenecId")},
            inverseJoinColumns={
                    @JoinColumn(name="PredmetID", referencedColumnName = "predmetId")}
    )
	protected Set<Predmet> predmeti;
	
	public Set<Ucitelj> getUcitelji() {
		return ucitelji;
	}

	public void setUcitelji(Set<Ucitelj> ucitelji) {
		this.ucitelji = ucitelji;
	}

	public long getUcenecId() {
		return ucenecId;
	}

	public void setUcenecId(long ucenecId) {
		this.ucenecId = ucenecId;
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

	public Date getDatumRojstva() {
		return datumRojstva;
	}

	public void setDatumRojstva(Date datumRojstva) {
		this.datumRojstva = datumRojstva;
	}

	public Set<Predmet> getPredmeti() {
		return predmeti;
	}

	public void setPredmeti(Set<Predmet> predmeti) {
		this.predmeti = predmeti;
	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ucenec)) return false;

        Ucenec ucenec = (Ucenec) o;

        if (!ime.equals(ucenec.ime)) return false;
        if (!priimek.equals(ucenec.priimek)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ime.hashCode();
        result = 31 * result + priimek.hashCode();
        return result;
    }
}
