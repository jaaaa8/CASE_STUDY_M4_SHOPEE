package com.example.case_study_mdl_4_shopee.entity;

import com.example.case_study_mdl_4_shopee.enums.TaskStatus;
import com.example.case_study_mdl_4_shopee.enums.TaskType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        indexes = {
                @Index(name = "idx_task_lookup", columnList = "warehouseId,type,status"),
                @Index(name = "idx_task_shipper", columnList = "shipperId"),
                @Index(name = "idx_task_date", columnList = "taskDate")
        }
)
public class ShippingTask {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @ManyToOne
    @JoinColumn(name = "shipperId")
    private Account shipper;

    @ManyToOne
    @JoinColumn(name = "warehouseId")
    private Warehouse warehouse;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private TaskType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    @Builder.Default
    private TaskStatus status = TaskStatus.CREATED;

    private LocalDateTime taskDate;

    @Column(nullable = false)
    @Builder.Default
    private Integer capacity = 20;

    @OneToMany(mappedBy = "shippingTask")
    @JsonIgnore
    private List<SubOrders> subOrders;
}
