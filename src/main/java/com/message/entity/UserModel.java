package com.message.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue
    @org.hibernate.annotations.Type(type="pg-uuid")
    private UUID id;
    @Column(name = "USER_NAME", nullable = false)
    private String name;
}
