package edix.tfg.consumoCombustiblebk.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


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
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="usuarios_tipos", joinColumns= @JoinColumn(name="usuario_id"),
	inverseJoinColumns=@JoinColumn(name="tipo_usuario_id"),
	uniqueConstraints= {@UniqueConstraint(columnNames= {"usuario_id", "tipo_usuario_id"})})
	private List<TipoUsuario> tiposUsuario;
	
	
	
	private static final long serialVersionUID = 1L;	
	
}
