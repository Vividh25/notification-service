package com.vividh.notificationservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Contact cannot be blank")
    private String contact;

    @NotEmpty(message = "At least one channel is required")
    @ElementCollection
    @CollectionTable(name = "user_channels", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "channel_type")
    private List<String> channelTypes;
}
