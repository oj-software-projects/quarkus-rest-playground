package org.example.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "Routes-DriverAreaHT")
public class RouteDriverArea extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "routesIDFK")
    public Route route;

    @ManyToOne
    @JoinColumn(name = "driverareaIDFK")
    public DriveArea driveArea;
}
