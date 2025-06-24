package org.example.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "VehicleSubtype")
public class VehicleSubtype extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicleSubtypeID")
    public Integer id;

    @Column(name = "vehicleSubtypeName", length = 50, nullable = false)
    public String vehicleSubtypeName;

    @OneToMany(mappedBy = "vehicleSubtype")
    public List<LocationVehicle> locationVehicles;
}
