package edix.tfg.consumoCombustiblebk.models.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Clase Entity que se corresponde con la entidad de base de datos
 * 
 * @author Luis Cifuentes
 * @author Jose A. Santos
 * @version 1.0
 * @since 13/11/2022
 *
 */

@Data
@Entity
@Table(name="empresas")
public class Empresa implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long empresaId;
	
	@Column(name="empresa_cif", length = 15, unique = true)
	private String cif;
	//	private String empresaCif;
	
	@Column(name="empresa_razon_social", length = 100)
	private String razonSocial;
	//	private String empresaRazonSocial;	

	private static final long serialVersionUID = 1L;
}
