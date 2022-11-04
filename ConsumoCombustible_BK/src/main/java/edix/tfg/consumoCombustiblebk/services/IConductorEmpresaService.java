package edix.tfg.consumoCombustiblebk.services;

import java.util.List;

import edix.tfg.consumoCombustiblebk.models.entity.ConductorEmpresa;

/**
 * Interfaz del Service
 * @author jsant
 *
 */
public interface IConductorEmpresaService {
	
	public ConductorEmpresa verConductorEmpresa(Long idConductor);
	public ConductorEmpresa nuevoConductorEmpresa(ConductorEmpresa nuevoConductor);
	public ConductorEmpresa actualizaConductorEmpresa(ConductorEmpresa updateConductor);
	public void eliminaConductorEmpresa(Long idConductor);
	public List<ConductorEmpresa> mostrarTodos();
	public List<ConductorEmpresa> mostrarConductorByUsuario(Long usuarioId);

}
