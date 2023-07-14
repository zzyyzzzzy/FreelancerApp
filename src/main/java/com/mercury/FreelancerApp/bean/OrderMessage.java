package com.mercury.FreelancerApp.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "ORDER_MESSAGE")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class OrderMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int userId;
    @Column
    private String message;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="ORDER_ID", nullable=false)
    @JsonBackReference
    private Order order;
    @Column
    @CreatedDate
    private Instant createdTime;
}
