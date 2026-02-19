package com.example.okr_back.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "owners")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String fullName;

    private String email;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @Column(nullable = false)
    private Boolean active = true;
}
