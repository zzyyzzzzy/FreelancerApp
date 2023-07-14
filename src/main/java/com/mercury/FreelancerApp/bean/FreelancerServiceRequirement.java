package com.mercury.FreelancerApp.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SERVICE_REQUIREMENT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FreelancerServiceRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String details;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="SERVICE_ID", nullable=false)
    @JsonBackReference
    private FreelancerService freelancerService;
}
