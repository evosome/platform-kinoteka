package org.example.modules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "HallLayout")
public class HallLayout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String linkToLayout;
    @OneToOne
    @JsonIgnore
    private Halls hall;
    public void addLay(Halls hall)
    {
       hall.setHallLayout(this);
       this.hall = hall;
    }
}
