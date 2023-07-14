package com.mercury.FreelancerApp.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "REVIEW")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int freelancerId;
    @Column
    private int clientId;
    @Column
    private int orderId;
    @Column
    private int rating;
    @Column
    private String comment;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="SERVICE_ID", nullable=false)
    @JsonBackReference
    private FreelancerService freelancerService;
    @Column
    @CreatedDate
    private Instant createdTime;
    @Column
    @LastModifiedDate
    private Instant updateTime;
}
