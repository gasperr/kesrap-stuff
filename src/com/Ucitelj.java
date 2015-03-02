package com;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Ucitelj implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue
	protected Long uciteljId;   
	
	protected String ime, priimek;
	
	@Version
	protected int version;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="ucitelj_ucenec",
            joinColumns={
                    @JoinColumn(name="UciteljID")},
            inverseJoinColumns={
                    @JoinColumn(name="UcenecID")}
    )

	protected Set<Ucenec> ucenci;
	
	@OneToMany(mappedBy = "ucitelj", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	protected Set<Predmet> predmeti;
	
	
	public Set<Ucenec> getUcenci() {
		return ucenci;
	}

	public void setUcenci(Set<Ucenec> ucenci) {
		this.ucenci = ucenci;
	}

	public long getUciteljId() {
		return uciteljId;
	}

	public void setUciteljId(long uciteljId) {
		this.uciteljId = uciteljId;
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

	public Set<Predmet> getPredmeti() {
		return predmeti;
	}

	public void setPredmeti(Set<Predmet> predmeti) {
		this.predmeti = predmeti;
	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ucitelj)) return false;

        Ucitelj ucitelj = (Ucitelj) o;

        if (!ime.equals(ucitelj.ime)) return false;
        if (!priimek.equals(ucitelj.priimek)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ime.hashCode();
        result = 31 * result + priimek.hashCode();
        return result;
    }
}
