package org.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.util.List;

@Entity
@Table(name = "Company")
public class Company extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "companyID")
    public Integer id;

    @Column(name = "companyName", length = 50, nullable = false)
    public String companyName;

    @Column(name = "companySortID")
    public Integer companySortId;

    @OneToMany(mappedBy = "company")
    public List<Driver> drivers;
}
