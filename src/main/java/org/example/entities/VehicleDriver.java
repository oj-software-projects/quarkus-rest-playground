package org.example.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "Vehicle-DriverHT")
public class VehicleDriver extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "vehicletypeIDFK")
    public VehicleTypes vehicleType;

    @ManyToOne
    @JoinColumn(name = "driverIDFK")
    public Driver driver;
}
