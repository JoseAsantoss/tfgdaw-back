package edix.tfg.consumoCombustiblebk.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edix.tfg.consumoCombustiblebk.models.entity.ConductorEmpresa;

/**
 * Interfaz que hereda de JpaRepository
 * @author jsant
 *
 */
@Repository
public interface IConductorEmpresaDao extends JpaRepository<ConductorEmpresa, Long> {

	@Query("select ce from ConductorEmpresa ce where ce.usuario.usuarioId = ?1")
	public List<ConductorEmpresa> findConductorEmpresaByUsuario(Long conductorId);
	
}
