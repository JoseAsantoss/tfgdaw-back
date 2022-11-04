package edix.tfg.consumoCombustiblebk.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@Table(name="vehiculos")
public class Vehiculo implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vehiculoId;
	
	@JsonProperty("vehiculoFechaCompra")
	@Column(name="VEHICULO_FECHA_COMPRA")
	@JsonFormat(pattern="dd/MM/yyy")
	@Temporal(TemporalType.DATE)
	private Date vehiculoFechaCompra;
	
	@Column(unique = true)
	private String vehiculoMatricula;
	
	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="USUARIO_ID")
	private Usuario usuario;

	//uni-directional many-to-one association to VersionesCoche
	@ManyToOne
	@JoinColumn(name="VERSION_ID")
	private VersionCoche versionCoche;	
	
	private static final long serialVersionUID = 1L;

}
