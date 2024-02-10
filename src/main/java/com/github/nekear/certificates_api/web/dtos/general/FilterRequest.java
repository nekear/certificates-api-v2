package com.github.nekear.certificates_api.web.dtos.general;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FilterRequest<Filter, SortCategories> {
    private Filter filters;

    private List<SortCriteria<SortCategories>> sorting = new LinkedList<>();

    private PaginationRequest pagination;

    /**
     * Utility function to build {@link Sort Spring Data JPA Sort} object from specified sorting parameters.
     *
     * @param columnNamesTransformers if columns names at {@link SortCategories} differ from real column names in the db, you can specify their relationship here.
     */
    public Sort getSorting(Map<SortCategories, String> columnNamesTransformers) {
        return Sort.by(sorting.stream().map(x -> new Sort.Order(
                x.getDirection(),
                columnNamesTransformers.containsKey(x.getColumn()) ? columnNamesTransformers.get(x.getColumn()) : x.getColumn().toString()
        )).toList().toArray(new Sort.Order[0]));
    }

    /**
     * Utility function, which forms {@link Pageable Spring Data JPA Pageable} object from specified sorting parameters including required Sorting.
     *
     * @param sorting specify here need search (can be acquired from getSorting method).
     */
    public Pageable formPageable(Sort sorting) {
        int askedPage = 0;
        int perPage = Integer.MAX_VALUE;

        if (pagination != null) {
            askedPage = pagination.getAskedPage();
            perPage = pagination.getElementsPerPage();
        }

        return PageRequest.of(askedPage, perPage, sorting == null ? Sort.unsorted() : sorting);
    }

    public Pageable formPageable() {
        return formPageable(null);
    }

    public Sort getSorting() {
        return getSorting(new HashMap<>());
    }
}
