package org.example.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "DriverFiles")
public class DriverFiles extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FileID")
    public Integer id;

    @Column(name = "FileName", length = 50)
    public String fileName;

    @Lob
    @Column(name = "FileDriver")
    public byte[] fileDriver;

    @ManyToOne
    @JoinColumn(name = "DriverFK")
    public Driver driver;

    @Column(name = "FileDate")
    public LocalDate fileDate;
}
