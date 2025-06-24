package org.example.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Depot")
public class Depot extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "depotID")
    public Integer id;

    @Column(name = "depotName", length = 50, nullable = false)
    public String depotName;

    @Column(name = "depotSortID")
    public Integer depotSortId;

    @ManyToOne
    @JoinColumn(name = "locationFK")
    public Location location;

    @OneToMany(mappedBy = "depot")
    public List<Driver> drivers;
}
