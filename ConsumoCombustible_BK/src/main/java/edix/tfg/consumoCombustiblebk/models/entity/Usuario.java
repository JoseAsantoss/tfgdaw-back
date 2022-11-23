package edix.tfg.consumoCombustiblebk.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
public class Usuario implements Serializable{

	@Id
	@Column(name="USUARIO_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long usuarioId;

	@Column(name="USUARIO_EMAIL")
	private String usuarioEmail;

	@Column(name="USUARIO_NOMBRE")
	private String usuarioNombre;

	@Column(name="USUARIO_APELLIDO1")
	private String usuarioApellido1;

	@Column(name="USUARIO_APELLIDO2")
	private String usuarioApellido2;
	
	private String password;
	
	private String username;
	
	private int enabled;
	
	//uni-directional many-to-one association to Empresas
	@ManyToOne
	@JoinColumn(name="EMPRESA_ID")
	private Empresa empresa;

	//uni-directional many-to-many association to Vehiculos
	@ManyToMany
	@JoinTable(
		name="usuarios_vehiculos"
		, joinColumns={
			@JoinColumn(name="USUARIO_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="VEHICULO_ID")
			}
		)
	private List<Vehiculo> vehiculos;
	
	//uni-directional many-to-many association to Rol
	@ManyToMany
	@JoinTable(
		name="usuarios_roles"
		, joinColumns={
			@JoinColumn(name="USUARIO_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ROL_ID")
			}
		)
	private List<Rol> roles;
	
	private static final long serialVersionUID = 1L;	
	
}
