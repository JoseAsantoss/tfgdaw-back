package edix.tfg.consumoCombustiblebk.models.entity;

import java.io.Serializable;

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
public class Empresa implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long empresaId;
	
	private String empresaCif;
	
	private String empresaRazonSocial;
	
	private static final long serialVersionUID = 1L;	
	
}
