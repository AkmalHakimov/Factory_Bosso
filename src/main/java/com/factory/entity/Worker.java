package com.factory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Worker {

    @Id
    @SequenceGenerator(name = "worker_seq", sequenceName = "worker_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "worker_seq")
    private Integer id;

    private String firstName;
    private String lastName;

    private Boolean sacked;
    private String role;
}
