package org.example.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Route-DriverHT")
public class RouteDriver extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routeDriverID")
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "routeIDFK")
    public Route route;

    @ManyToOne
    @JoinColumn(name = "driverIDFK")
    public Driver driver;

    @Column(name = "routeDate")
    public LocalDate routeDate;

    @Column(name = "driverRouteSortID")
    public Integer driverRouteSortId;
}
