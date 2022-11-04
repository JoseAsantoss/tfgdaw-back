package edix.tfg.consumoCombustiblebk.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * Clase Entity que se corresponde con la entidad de base de datos
 * 
 * @author Luis Cifuentes
 * @author Jose A. Santos
 * @version 1.0
 * @since 03/11/2022
 *
 */
@Data
@Entity
@Table(name="conductor_empresa")
public class ConductorEmpresa implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long conductorId;
	
	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="USUARIO_ID")
	private Usuario usuario;
	
	@Column(unique = true)
	private String conductorAlias;
	
	private String conductorNombre;
	
	private String conductorApellido1;
	
	private String conductorApellido2;
	
	private String conductorPassword;
	
	private static final long serialVersionUID = 1L;

}
