package edix.tfg.consumoCombustiblebk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edix.tfg.consumoCombustiblebk.models.entity.ModeloCoche;

@Repository
public interface IModeloCocheDao extends JpaRepository<ModeloCoche, Long> {
	
	@Query("SELECT mc FROM ModeloCoche mc "
			+ "WHERE mc.marcaCoche.marcaId = ?1")
	public List<ModeloCoche> findByMarcaId(Long marcaId);

}
