package org.example.modules;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
@Getter
@Setter
@Entity
@Table(name = "Feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long feedbackId;
    private String feedbackText;
    @ManyToOne
    @JsonIgnore
    private Film filmFk;
    @ManyToOne
    @JsonIgnore
    private MovieUser movieUserFk;

}