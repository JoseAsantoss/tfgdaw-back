package edix.tfg.consumoCombustiblebk.services;

import java.util.List;

import edix.tfg.consumoCombustiblebk.models.entity.TipoUsuario;

/**
 * Interface de la capa de servicio de la aplicacion
 * @author Luis Cifuentes
 * @author Jose A. Santos
 * @version 1.0
 * @since 25/10/2022
 *
 */
public interface ITipoUsuarioService {

	public List<TipoUsuario> findAll();
	public TipoUsuario findById(Long idTipo);
	public TipoUsuario createTipoUsuario(TipoUsuario tipoUsuario);
	public TipoUsuario updateTipoUsuario(TipoUsuario tipoUsuario);
	public void deleteTipoUsuario(Long idTipo);
}
