package edix.tfg.consumoCombustiblebk.services;

import java.util.List;

import edix.tfg.consumoCombustiblebk.models.entity.ModeloCoche;

public interface IModeloCocheService {

	public ModeloCoche showByModeloId(Long modeloId);
	public ModeloCoche showByModeloNombre(Long marcaId, String modeloNombre);
	public ModeloCoche borrarByModeloId(Long modeloId);
	public List<ModeloCoche> listAllModelosFromMarca(Long marcaCocheId);
	public ModeloCoche addModeloCoche(ModeloCoche modeloCocheJson);
}
