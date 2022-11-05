package edix.tfg.consumoCombustiblebk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edix.tfg.consumoCombustiblebk.models.entity.Repostaje;

@Repository
public interface IRepostajeDao extends JpaRepository<Repostaje, Long> {
	
	@Query("select rep from Repostaje rep where rep.vehiculo.vehiculoId = ?1")
	public List<Repostaje> repostajesByIdVehiculo(Long vehiculoId);

}
