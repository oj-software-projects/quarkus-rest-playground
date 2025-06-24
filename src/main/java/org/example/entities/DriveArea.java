package org.example.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "DriveArea")
public class DriveArea extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driveareaID")
    public Integer id;

    @Column(name = "driveareaName", length = 50, nullable = false)
    public String driveareaName;

    @OneToMany(mappedBy = "driveArea")
    public List<RouteDriverArea> routeDriverAreas;
}
