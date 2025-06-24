package org.example.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Vehicletypes")
public class VehicleTypes extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicletypeID")
    public Integer id;

    @Column(name = "vehicletypeName", length = 50, nullable = false)
    public String vehicleName;

    @Column(name = "vehicletypeSortID")
    public Integer vehicleSortId;

    @OneToMany(mappedBy = "vehicleType")
    public List<LocationVehicle> locationVehicles;

    @OneToMany(mappedBy = "vehicleType")
    public List<VehicleDriver> vehicleDrivers;
}
