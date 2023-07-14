package com.mercury.FreelancerApp.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
@Entity
@Table(name = "ORDER_INFO")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class OrderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String requirementAnswer;
    @Column
    private int requirementId;
    @Column
    private int uploadBy; // which user uploaded it
    @Column
    @CreatedDate
    private Instant createdTime;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="ORDER_ID", nullable=false)
    @JsonBackReference
    private Order order;

//    @ManyToOne(cascade = CascadeType.DETACH)
//    @JoinColumn(name="REQUIREMENT_ID", nullable=false)
//    @JsonBackReference
//    private FreelancerServiceRequirement serviceRequirement;
}
