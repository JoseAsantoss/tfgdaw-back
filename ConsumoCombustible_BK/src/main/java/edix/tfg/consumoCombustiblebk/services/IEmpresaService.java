package edix.tfg.consumoCombustiblebk.services;

import java.util.List;

import edix.tfg.consumoCombustiblebk.models.entity.Empresa;

public interface IEmpresaService {

	List<Empresa> listarEmpresas();
	Empresa altaEmpresa(Empresa empresa);
	void deleteEmpresa(Long idEmpresa);
	Empresa updateEmpresa(Empresa empresa);
	Empresa buscarEmpresaId(Long idEmpresa);
	Empresa buscarEmpresaCif(String cifEmpresa);
	
}
