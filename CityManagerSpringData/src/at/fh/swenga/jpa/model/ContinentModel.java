package at.fh.swenga.jpa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Version;

@Entity
public class ContinentModel {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
    @OneToMany(mappedBy="continent",fetch=FetchType.LAZY)
    @OrderBy("cityName, countryName")
    private Set<CityModel> cities;

	@Version
	long version;
    
    
    public ContinentModel() {
		// TODO Auto-generated constructor stub
    }
    
	public ContinentModel(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<CityModel> getCities() {
		return cities;
	}

	public void setCities(Set<CityModel> cities) {
		this.cities = cities;
	}
	
	public void addCity(CityModel city) {
		if (cities==null) {
			cities= new HashSet<CityModel>();
		}
		cities.add(city);
	}
	
    
}
