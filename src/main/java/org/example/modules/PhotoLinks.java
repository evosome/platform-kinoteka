package org.example.modules;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "PhotoLinks")
public class PhotoLinks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JsonIgnore
    private Film filmFromLink;
    private String link;
}
