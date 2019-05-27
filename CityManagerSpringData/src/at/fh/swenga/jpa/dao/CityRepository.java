package at.fh.swenga.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.CityModel;

@Repository
@Transactional
public interface CityRepository extends JpaRepository<CityModel, Integer> {

	List<CityModel> findByCityName(String searchString);

	List<CityModel> findByCountryName(String searchString);
	
	@Query("SELECT e FROM CityModel AS e WHERE e.cityName like :searchString OR e.countryName like :searchString")
	List<CityModel> findByCityNameOrByCountryName(@Param("searchString") String searchString);

	List<CityModel> findByNamedQuery(String searchString);

	int countByCityName(String searchString);

	List<CityModel> deleteByCityName(String searchString);

	List<CityModel> findByCityNameContainingOrCountryNameContainingAllIgnoreCase(String cityName, String countryName);

	List<CityModel> deleteByContinentName(String searchString);

	List<CityModel> findByOrderByCityName();

	List<CityModel> findTop10ByOrderByCityName();

	List<CityModel> findByContinentNameOrderByCityNameAsc(String searchString);

	@Query("SELECT e FROM CityModel AS e WHERE e.population <= cast(:searchString as int)")
	List<CityModel> findByPopulationLessThanEqual(@Param("searchString") String searchString);

	@Query("SELECT e FROM CityModel AS e WHERE e.population BETWEEN 100000 AND 250000")
	List<CityModel> findByPopulationBetween();

	List<CityModel> findByContinentName(String searchString);
	
}
