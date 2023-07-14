package com.mercury.FreelancerApp.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "ORDER_FILE")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class OrderFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String fileName;
    @Column
    private String fileLocation;
    @Column
    private int uploadBy; // which user uploaded it
    @Column
    @CreatedDate
    private Instant createdTime;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="ORDER_ID", nullable=false)
    @JsonBackReference
    private Order order;
}
