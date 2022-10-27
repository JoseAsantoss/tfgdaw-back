package edix.tfg.consumoCombustiblebk.models.entity;

import java.io.Serializable;

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
 * @since 25/10/2022
 *
 */
@Data
@Entity
@Table(name="usuarios")
public class Usuario implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long usuarioId;
	
	private String usuarioEmail;
	
	private String usuarioNombre;
	
	private String usuarioApellido1;
	
	private String usuarioApellido2;
	
	private String usuarioPassword;
	
	@ManyToOne
	@JoinColumn(name="TIPO_USUARIO_ID")
	private TipoUsuario tipoUsuario;
	
	private static final long serialVersionUID = 1L;	
	
}
