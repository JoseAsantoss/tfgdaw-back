package edix.tfg.consumoCombustiblebk.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="mantenimientos")
public class Mantenimiento implements Serializable {
	
	@Id
	@Column(name="MANTENIMIENTO_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mantenimientoId;

	@Column(name="MANTENIMIENTO_DETALLE")
	private String mantenimientoDetalle;
	
	@Temporal(TemporalType.DATE)
	@Column(name="MANTENIMIENTO_FECHA")
	private Date mantenimientoFecha;

	@Column(name="MANTENIMIENTO_IMPORTE")
	private BigDecimal mantenimientoImporte;
	
	@Column(name="MANTENIMIENTO_KM")
	private Long mantenimientoKM;

	@Column(name="MANTENIMIENTO_OBSERVACIONES")
	private String mantenimientoObservaciones;
	
	@ManyToOne
	@JoinColumn(name="VEHICULO_ID")
	private Vehiculo vehiculo;
	
	
	private static final long serialVersionUID = 1L;

}
