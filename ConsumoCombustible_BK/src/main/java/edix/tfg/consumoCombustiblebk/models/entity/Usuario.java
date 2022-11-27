package edix.tfg.consumoCombustiblebk.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
	@Column(name="USUARIO_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long usuarioId;

	@Column(name="USUARIO_EMAIL", unique = true)
	private String usuarioEmail;

	@Column(name="USUARIO_NOMBRE")
	private String usuarioNombre;

	@Column(name="USUARIO_APELLIDO1")
	private String usuarioApellido1;

	@Column(name="USUARIO_APELLIDO2")
	private String usuarioApellido2;
	
	private String password;
	
	private String username;	

	private Boolean enabled;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="empresa_id")
	private Empresa empresa;
	
	@ManyToMany(fetch = FetchType.EAGER/*, cascade = CascadeType.ALL*/)
	@JoinTable(name="usuarios_roles", joinColumns= @JoinColumn(name="usuario_id"),
	inverseJoinColumns=@JoinColumn(name="rol_id"),
	uniqueConstraints= {@UniqueConstraint(columnNames= {"usuario_id", "rol_id"})})
	private List<Rol> roles;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="usuarios_vehiculos", joinColumns= @JoinColumn(name="usuario_id"),
	inverseJoinColumns=@JoinColumn(name="vehiculo_id"),
	uniqueConstraints= {@UniqueConstraint(columnNames= {"usuario_id", "vehiculo_id"})})
	private List<Vehiculo> vehiculos;
	
	private static final long serialVersionUID = 1L;	
	
}
