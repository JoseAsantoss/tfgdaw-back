package edix.tfg.consumoCombustiblebk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edix.tfg.consumoCombustiblebk.models.entity.VersionCoche;

@Repository
public interface IVersionCocheDao extends JpaRepository<VersionCoche, Long> {

	@Query("SELECT vc FROM VersionCoche vc "
			+ "WHERE vc.modeloCoche.modeloId = ?1")
	public List<VersionCoche> findByModeloId(Long modeloId);
	
	
	public VersionCoche findByVersionNombre(String nombre);
}
