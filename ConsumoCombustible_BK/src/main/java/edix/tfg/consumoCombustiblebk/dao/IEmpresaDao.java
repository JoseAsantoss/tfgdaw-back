package edix.tfg.consumoCombustiblebk.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edix.tfg.consumoCombustiblebk.models.entity.Empresa;

@Repository
public interface IEmpresaDao extends JpaRepository<Empresa, Long> {
	
	/*@Query("SELECT e FROM Empresa e "
			+ "WHERE e.cif = ?1")*/
	public Empresa findByCif(String cif);

}
