package at.fh.swenga.jpa.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.repository.Query;

@Entity
@Table(name = "City")

@NamedQueries({
	@NamedQuery(name="CityModel.findByNamedQuery",
			query="SELECT e FROM CityModel AS e WHERE e.cityName like ?1 OR e.countryName like ?1")
})

public class CityModel implements java.io.Serializable {


	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 30)
	private String cityName;
	 
	@Column(nullable = false, length = 30)
	private String countryName;
	
	@Column(nullable = false, length = 30)
	private String stateName;
	
	@Column(nullable = false)
	private int population;

	
	@ManyToOne (cascade = CascadeType.PERSIST)
	private ContinentModel continent;

	
	public CityModel() {
	}

	public CityModel(String cityName, String countryName, String stateName, int population) {
		super();
		this.cityName = cityName;
		this.countryName = countryName;
		this.stateName = stateName;
		this.population = population;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public ContinentModel getContinent() {
		return continent;
	}

	public void setContinent(ContinentModel continent) {
		this.continent = continent;
	}
	
	
	
}
