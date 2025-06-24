package org.example.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Routes")
public class Route extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routeID")
    public Integer id;

    @Column(name = "routeName", length = 50)
    public String routeName;

    @Column(name = "routeSortID")
    public Integer routeSortId;

    @OneToMany(mappedBy = "route")
    public List<RouteDriverArea> routeDriverAreas;

    @OneToMany(mappedBy = "route")
    public List<RouteDriver> routeDrivers;
}
