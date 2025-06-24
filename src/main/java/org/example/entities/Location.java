package org.example.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Location")
public class Location extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locationID")
    public Integer id;

    @Column(name = "locationName", length = 50, nullable = false)
    public String locationName;

    @Column(name = "locationToken", length = 50, nullable = false)
    public String locationToken;

    @OneToMany(mappedBy = "location")
    public List<Depot> depots;

    @OneToMany(mappedBy = "location")
    public List<Driver> drivers;

    @OneToMany(mappedBy = "location")
    public List<LocationVehicle> locationVehicles;
}
