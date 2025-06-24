package org.example.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Service")
public class Service extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serviceID")
    public Integer id;

    @Column(name = "serviceName", length = 50, nullable = false)
    public String serviceName;

    @Column(name = "serviceSortID")
    public Integer serviceSortId;

    @OneToMany(mappedBy = "service")
    public List<Driver> drivers;
}
