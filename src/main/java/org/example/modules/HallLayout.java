package org.example.modules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import org.example.jsontype.MyJson;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(name = "HallLayout")

public class HallLayout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JdbcTypeCode(SqlTypes.JSON)
    //@Column(columnDefinition = "jsonb")
    private MyJson linkToLayout;

    @OneToOne
    @JsonIgnore
    private Halls hall;

    public void addLay(Halls hall) {
        hall.setHallLayout(this);
        this.hall = hall;
    }
}
