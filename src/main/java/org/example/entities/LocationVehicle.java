package org.example.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "Location-VehicleHT")
public class LocationVehicle extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicleSetID")
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "locationIDFK")
    public Location location;

    @ManyToOne
    @JoinColumn(name = "vehicletypeIDFK")
    public VehicleTypes vehicleType;

    @ManyToOne
    @JoinColumn(name = "vehicleSubtypeIDFK")
    public VehicleSubtype vehicleSubtype;
}
