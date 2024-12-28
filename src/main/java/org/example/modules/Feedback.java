package org.example.modules;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long feedbackId;
    @Column(columnDefinition = "Integer default 0")
    private int mark = 0;
    private String feedbackText;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate;
    @ManyToOne
    @JsonIgnore
    private Film filmFk;
    @ManyToOne
    private MovieUser movieUserFk;

}