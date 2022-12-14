package edix.tfg.consumoCombustiblebk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edix.tfg.consumoCombustiblebk.models.entity.Usuario;
import edix.tfg.consumoCombustiblebk.models.entity.Vehiculo;

/**
 * Interfaz de la capa Dao anotada como Repository que extiende de
 * la Interface JpaRepository<>
 * 
 * @author Luis Cifuentes
 * @author Jose A. Santos
 * @version 1.0
 * @since 25/10/2022
 *
 */
@Repository
public interface IUsuarioDao extends JpaRepository<Usuario, Long>{
	
	
	Usuario findByUsuarioEmail(String email);
	
	@Query("SELECT usr from Usuario usr "
			+ "WHERE usr.usuarioEmail LIKE %?1% ")
	public List<Usuario> busquedaUsuarioEmail(String busqueda);
	
	@Query("SELECT usr from Usuario usr "
			+ "WHERE usr.empresa.cif = ?1 ")
	public List<Usuario> listarUsuariosEmpresa(String empresaCif);
	
	@Query("SELECT usr from Usuario usr "
			+ "INNER JOIN usr.roles ur "
			+ "WHERE usr.empresa.cif = ?1 "
			+ "AND ur.rolDescripcion = ?2")
	public List<Usuario> listarConductoresEmpresa(String empresaCif, String rolDescripcion);
	
	@Query("SELECT COUNT(usr) from Usuario usr "
			+ "INNER JOIN usr.roles ur "
			+ "WHERE usr.empresa.cif = ?1 "
			+ "AND ur.rolDescripcion = ?2")
	public Integer contarConductoresEmpresa(String empresaCif, String rolDescripcion);
	
	@Query("SELECT usr from Usuario usr "
			+ "WHERE usr.usuarioNombre LIKE %?1% ")
	public List<Usuario> busquedaUsuarioNombre(String busqueda);
	
	@Query("SELECT usr from Usuario usr "
			+ "WHERE usr.usuarioApellido1 LIKE %?1% ")
	public List<Usuario> busquedaUsuarioApellido1(String busqueda);

	@Query("SELECT usr from Usuario usr "
			+ "WHERE usr.usuarioApellido2 LIKE %?1% ")
	public List<Usuario> busquedaUsuarioApellido2(String busqueda);

	@Query("SELECT u.vehiculos FROM Usuario u "
			+ "INNER JOIN u.vehiculos uv "
			+ "WHERE u.usuarioId = ?1 "
			+ "AND uv.vehiculoMatricula LIKE %?2% ")
	public List<Vehiculo> listarVehiculosUsuario(Long usuarioId, String matricula);

	@Query("SELECT u.vehiculos FROM Usuario u "
			+ "INNER JOIN u.vehiculos uv "
			+ "INNER JOIN uv.versionCoche vc "
			+ "WHERE u.usuarioId = ?1 "
			+ "AND vc.versionNombre LIKE %?2% ")
	public List<Vehiculo> listarVehiculosUsuarioVersion(Long usuarioId, String version);

	@Query("SELECT u.vehiculos FROM Usuario u "
			+ "INNER JOIN u.vehiculos uv "
			+ "WHERE u.usuarioId = ?1 ")
	public List<Vehiculo> listarVehiculosUsuario(Long usuarioId);

	@Query("SELECT COUNT(uv) FROM Usuario u "
			+ "INNER JOIN u.vehiculos uv "
			+ "WHERE u.usuarioId = ?1 ")
	public Integer contarVehiculosUsuario(Long usuarioId);

}
