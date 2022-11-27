package edix.tfg.consumoCombustiblebk.services;

import java.util.List;

import edix.tfg.consumoCombustiblebk.models.entity.Rol;

/**
 * Interface de la capa de servicio de la aplicacion
 * @author Luis Cifuentes
 * @author Jose A. Santos
 * @version 1.0
 * @since 25/10/2022
 *
 */
public interface IRolUsuarioService {

	public List<Rol> findAll();
	public Rol findById(Long idTipo);
	public Rol createTipoUsuario(Rol tipoUsuario);
	public Rol updateTipoUsuario(Rol tipoUsuario);
	public void deleteTipoUsuario(Long idTipo);
	Rol findByRolName(String descripcion);
}
