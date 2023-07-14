package com.mercury.FreelancerApp.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "APP_ORDER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int freelancerId;
    @Column
    private int clientId;
    @Column
    private int orderStatus;
    @Column
    private double orderSubTotal;
    @Column
    private double taxTotal;
    @Column
    private double tipsTotal;
    @Column
    private Timestamp dueDate;
    @Column
    @CreatedDate
    private Instant createdTime;
    @Column
    @LastModifiedDate
    private Instant updateTime;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="SERVICE_ID", nullable=false)
    @JsonBackReference
    private FreelancerService freelancerService;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @JsonManagedReference
    private List<OrderInfo> orderInfoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @JsonManagedReference
    private List<OrderFile> orderFiles;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @JsonManagedReference
    private List<OrderMessage> orderMessages;
}
