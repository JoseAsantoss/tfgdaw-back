package edix.tfg.consumoCombustiblebk.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edix.tfg.consumoCombustiblebk.dao.IEmpresaDao;
import edix.tfg.consumoCombustiblebk.models.entity.Empresa;
import edix.tfg.consumoCombustiblebk.services.IEmpresaService;

@Service
public class EmpresaServiceImpl implements IEmpresaService {

	@Autowired
	IEmpresaDao iEmpresaDao;
	
	@Override
	public List<Empresa> listarEmpresas() {
		return iEmpresaDao.findAll();
	}

	@Override
	public Empresa altaEmpresa(Empresa empresa) {
		if (empresa == null) {
			return null;
		}else {
			try {
				Empresa emp = iEmpresaDao.saveAndFlush(empresa);
				return emp;
			}catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}
		}
		
	}

	@Override
	public Empresa buscarEmpresaId(Long idEmpresa) {
		return iEmpresaDao.findById(idEmpresa).orElse(null);
	}

	@Override
	public Empresa buscarEmpresaCif(String cifEmpresa) {
		return iEmpresaDao.findByCif(cifEmpresa);
	}

	@Override
	public void deleteEmpresa(Long idEmpresa) {
		iEmpresaDao.deleteById(idEmpresa);

	}

	@Override
	public Empresa updateEmpresa(Long empresaId, Empresa empresa) {
		empresa.setEmpresaId(empresaId);
		return iEmpresaDao.save(empresa);
	}

}
