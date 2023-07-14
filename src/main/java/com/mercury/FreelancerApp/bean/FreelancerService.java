package com.mercury.FreelancerApp.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mercury.FreelancerApp.bean.constant.FreelancerServiceCategory;
import com.mercury.FreelancerApp.bean.constant.FreelancerServiceStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "SERVICE")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)

public class FreelancerService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int userId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private String description;
    @Column
    private String thumbImage;
    @Column
    @Enumerated(EnumType.STRING)
    private FreelancerServiceStatus serviceStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FreelancerServiceCategory serviceCategory;

    @Column
    @CreatedDate
    private Instant createdTime;

    @Column
    @LastModifiedDate
    private Instant updateTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "freelancerService")
    @JsonManagedReference
    private List<FreelancerServiceFile> serviceFiles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "freelancerService")
    @JsonManagedReference
    private List<FreelancerServiceRequirement> serviceRequirements;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "freelancerService")
    @JsonManagedReference
    private List<Review> reviews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "freelancerService")
    @JsonManagedReference
    @JsonIgnoreProperties("freelancerService")
    private List<Order> serviceOrders;
    @Override
    public String toString() {
        return "FreelancerService{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", thumbImage='" + thumbImage + '\'' +
                ", serviceStatus=" + serviceStatus +
                ", serviceCategory=" + serviceCategory +
                ", createdTime=" + createdTime +
                '}';
    }
}

