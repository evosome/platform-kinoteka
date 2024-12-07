package org.example.modules;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "BrowsingHistory")
public class BrowsingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long historyId;
//    @ManyToMany
//    private List<Film> history = new ArrayList<>();
    @OneToOne
    private MovieUser movieUser;
    public void addHistory(MovieUser movieUser)
    {
        movieUser.setBrowsingHistory(this);
        this.movieUser = movieUser;
    }
}
