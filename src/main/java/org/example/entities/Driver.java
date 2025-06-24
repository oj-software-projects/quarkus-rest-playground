package org.example.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Driver")
public class Driver extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driverID")
    public Integer id;

    @Column(name = "personalNr", nullable = false)
    public Integer personalNr;

    @Column(name = "employeeNr", nullable = false)
    public Integer employeeNr;

    @Column(name = "name", length = 50, nullable = false)
    public String name;

    @Column(name = "firstname", length = 50, nullable = false)
    public String firstname;

    @Column(name = "birthday")
    public LocalDate birthday;

    @Lob
    @Column(name = "picture")
    public byte[] picture;

    @Lob
    @Column(name = "image")
    public byte[] image;

    @Column(name = "imageUri")
    public String imageUri;

    @Column(name = "externalCompany")
    public Boolean externalCompany;

    @Column(name = "archived")
    public Boolean archived;

    @ManyToOne
    @JoinColumn(name = "companyFK")
    public Company company;

    @ManyToOne
    @JoinColumn(name = "depotFK")
    public Depot depot;

    @ManyToOne
    @JoinColumn(name = "serviceFK")
    public Service service;

    @ManyToOne
    @JoinColumn(name = "locationIDFK")
    public Location location;

    @Column(name = "shuntservice")
    public Boolean shuntservice;

    @Column(name = "dateSTRAB")
    public LocalDate dateStrab;

    @Column(name = "dateSTRABohne")
    public LocalDate dateStrabOhne;

    @Column(name = "dateEBO")
    public LocalDate dateEbo;

    @Column(name = "dateEBOohne")
    public LocalDate dateEboOhne;

    @Column(name = "STRAB")
    public Boolean strab;

    @Column(name = "STRABohne")
    public Boolean strabOhne;

    @Column(name = "EBO")
    public Boolean ebo;

    @Column(name = "EBOohne")
    public Boolean eboOhne;

    // Many other fields omitted for brevity

    @OneToMany(mappedBy = "driver")
    public List<DriverFiles> driverFiles;

    @OneToMany(mappedBy = "driver")
    public List<RouteDriver> routeDrivers;

    @OneToMany(mappedBy = "driver")
    public List<VehicleDriver> vehicleDrivers;
}
