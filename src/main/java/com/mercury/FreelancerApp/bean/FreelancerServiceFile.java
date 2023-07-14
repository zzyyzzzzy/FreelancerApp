package com.mercury.FreelancerApp.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(name = "SERVICE_FILE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FreelancerServiceFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String fileName;
    @Column(nullable = false)
    private String fileLocation;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="SERVICE_ID", nullable=false)
    @JsonBackReference
    private FreelancerService freelancerService;
}
