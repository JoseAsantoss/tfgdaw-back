package edix.tfg.consumoCombustiblebk.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edix.tfg.consumoCombustiblebk.models.entity.Rol;

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
public interface ITipoUsuarioDao extends JpaRepository<Rol, Long>{

}
