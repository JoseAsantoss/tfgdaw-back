package edix.tfg.consumoCombustiblebk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edix.tfg.consumoCombustiblebk.models.entity.Vehiculo;

@Repository
public interface IVehiculoDao extends JpaRepository<Vehiculo, Long> {

	@Query("select vh from Vehiculo vh "
			+ "where vh.usuario.usuarioId = ?1")
	public List<Vehiculo> findVehiculosByUsuario(Long usuarioId);
	
	@Query("SELECT vehiculos FROM Usuario usr "
			+ "INNER JOIN usr.vehiculos uv"
			+ "WHERE uv.usuarioId = ?1 "
			+ "AND Vehiculo.vehiculoMatricula LIKE %?2%")
	public List<Vehiculo> busquedaVehiculosUsuario(Long usuarioId, String busqueda);
	
	@Query("SELECT vh from Vehiculo vh "
			+ "WHERE vh.versionCoche.versionNombre LIKE %?1% "
			+ "OR vh.versionCoche.modelosCoche.modeloNombre LIKE %?1% "
			+ "OR vh.versionCoche.modelosCoche.marcasCoche.marcaNombre LIKE %?1% ")
	public List<Vehiculo> busquedaVehiculosDescripcion(String busqueda);

	@Query("SELECT vh from Vehiculo vh "
			+ "WHERE vh.usuario.usuarioId = ?1 "
			+ "AND (vh.versionCoche.versionNombre LIKE %?2% "
			+ "OR vh.versionCoche.modelosCoche.modeloNombre LIKE %?2% "
			+ "OR vh.versionCoche.modelosCoche.marcasCoche.marcaNombre LIKE %?2% )")
	public List<Vehiculo> busquedaVehiculosUsuarioDescripcion(Long usuarioId, String busqueda);

}
