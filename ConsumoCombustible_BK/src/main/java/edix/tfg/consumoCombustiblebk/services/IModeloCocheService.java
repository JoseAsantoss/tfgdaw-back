package edix.tfg.consumoCombustiblebk.services;

import java.util.List;

import edix.tfg.consumoCombustiblebk.models.entity.MarcaCoche;
import edix.tfg.consumoCombustiblebk.models.entity.ModeloCoche;

public interface IModeloCocheService {

	public ModeloCoche showByModeloId(Long modeloId);
	//public List<ModeloCoche> listAllModelosFromMarca(MarcaCoche marca);
	public List<ModeloCoche> listAllModelosFromMarca(Long marcaCocheId);
}
