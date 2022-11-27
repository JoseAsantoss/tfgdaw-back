package edix.tfg.consumoCombustiblebk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edix.tfg.consumoCombustiblebk.models.entity.Vehiculo;

@Repository
public interface IVehiculoDao extends JpaRepository<Vehiculo, Long> {

	@Query("select vehiculos from Usuario u "
			+ "where u.usuarioId = ?1")
	public List<Vehiculo> findVehiculosByUsuario(Long usuarioId);
			
	/*@Query("select vehiculos from Usuario u "
			+ "INNER JOIN vehiculos vh "
			+ "where u.usuarioId = ?1 "
			+ "AND vh.vehiculoMatricula LIKE %?2%")
	public List<Vehiculo> busquedaVehiculosUsuario(Long usuarioId, String busqueda);*/
	
	@Query(value = "SELECT * FROM vehiculos " + 
	"JOIN usuarios_vehiculos" + 
	"ON vehiculos.vehiculo_id = usuarios_vehiculos.vehiculo_id" + 
	"JOIN usuarios" + 
	"ON usuarios.usuario_id = usuarios_vehiculos.usuario_id" + 
	"WHERE usuarios.usuario_id = :usuarioId "
	+ "AND vehiculos.vehiculo_matricula LIKE '%:busqueda%';", 
			  nativeQuery = true)
	public List<Vehiculo> busquedaVehiculosUsuario(
			@Param("usuarioId") Long usuarioId, 
			@Param("busqueda") String busqueda);
	
	/*@Query("SELECT vh from Vehiculo vh "
			+ "WHERE vh.versionCoche.versionNombre LIKE %?1% "
			+ "OR vh.versionCoche.modeloCoche.modeloNombre LIKE %?1% "
			+ "OR vh.versionCoche.modeloCoche.marcaCoche.marcaNombre LIKE %?1% ")
	public List<Vehiculo> busquedaVehiculosDescripcion(String busqueda);

	@Query("SELECT vh from Vehiculo vh "
			+ "WHERE vh.usuario.usuarioId = ?1 "
			+ "AND (vh.versionCoche.versionNombre LIKE %?2% "
			+ "OR vh.versionCoche.modeloCoche.modeloNombre LIKE %?2% "
			+ "OR vh.versionCoche.modeloCoche.marcaCoche.marcaNombre LIKE %?2% )")
	public List<Vehiculo> busquedaVehiculosUsuarioDescripcion(Long usuarioId, String busqueda);*/

}
