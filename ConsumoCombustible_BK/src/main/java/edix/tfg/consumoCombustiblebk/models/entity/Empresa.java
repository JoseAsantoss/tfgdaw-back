package edix.tfg.consumoCombustiblebk.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="empresas")
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long empresaId;
	
	@Column(name="empresa_cif", length = 15, unique = true)
	private String cif;
	
	@Column(name="empresa_razon_social", length = 100)
	private String razonSocial;
}
