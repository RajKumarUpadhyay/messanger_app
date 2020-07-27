package com.message.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Table
@Entity
@Data
@NoArgsConstructor
@ToString
public class MessageHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @org.hibernate.annotations.Type(type="pg-uuid")
    private UUID sender;
    @org.hibernate.annotations.Type(type="pg-uuid")
    private UUID receiver;
    private String message_body;
}
