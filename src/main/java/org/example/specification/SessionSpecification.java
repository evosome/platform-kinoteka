package org.example.specification;

import org.example.modules.Session;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.data.jpa.domain.Specification;

public class SessionSpecification {

    public static Specification<Session> filterByDate(String date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null || date.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("date"), date);
        };
    }

    public static Specification<Session> filterByCinemaType(String cinemaType) {
        return (root, query, criteriaBuilder) -> {
            if (cinemaType == null || cinemaType.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("cinemaType"), cinemaType);
        };
    }

    public static Specification<Session> filterByHallId(Long hallId) {
        return (root, query, criteriaBuilder) -> {
            if (hallId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("hallsFk").get("sessionId"), hallId); // Предположим, "sessionId" это поле в объектах Halls
        };
    }

    public static Specification<Session> combineFilters(String date, String cinemaType, Long hallId) {
        return Specification.where(filterByDate(date))
                .and(filterByCinemaType(cinemaType))
                .and(filterByHallId(hallId));
    }
}