package edix.tfg.consumoCombustiblebk.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edix.tfg.consumoCombustiblebk.models.entity.Combustible;

@Repository
public interface ICombustibleDao extends JpaRepository<Combustible, Long> {
	
	
}
