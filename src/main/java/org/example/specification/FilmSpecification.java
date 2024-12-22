package org.example.specification;
import org.example.modules.Film;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class FilmSpecification {

    public static Specification<Film> filterByGenres(List<String> genreNames) {
        return (root, query, criteriaBuilder) -> {
            if (genreNames == null || genreNames.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.join("genres").get("genreName").in(genreNames);
        };
    }
    public static Specification<Film> filterByDirectorNames(List<String> directorNames) {
        return (root, query, criteriaBuilder) -> {
            if (directorNames == null || directorNames.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.join("producers").get("name").in(directorNames);
        };
    }
    public static Specification<Film> filterByCountry(List<String> countryNames) {
        return (root, query, criteriaBuilder) -> {
            if (countryNames == null || countryNames.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.join("countries").get("countryName").in(countryNames);
        };
    }

    public static Specification<Film> filterByTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null || title.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("title"), "%" + title + "%");
        };
    }

    public static Specification<Film> filterByAgeRestriction(Integer ageRestriction) {
        return (root, query, criteriaBuilder) -> {
            if (ageRestriction == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("ageRestriction"), ageRestriction);
        };
    }

    public static Specification<Film> filterBySessionHallId(Long hallId) {
        return (root, query, criteriaBuilder) -> {
            if (hallId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.join("sessions").join("hallsFk").get("hallId"), hallId);
        };
    }

    public static Specification<Film> combineFilters(List<String> genreNames,
                                                     List<String> countryNames,
                                                     String title,
                                                     Integer ageRestriction,
                                                     Long hallId,
                                                     List<String> directorNames) {
        return Specification.where(filterByGenres(genreNames))
                .and(filterByCountry(countryNames))
                .and(filterByTitle(title))
                .and(filterByAgeRestriction(ageRestriction))
                .and(filterBySessionHallId(hallId))
                .and(filterByDirectorNames(directorNames));
    }
}
