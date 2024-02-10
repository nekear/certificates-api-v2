package com.github.nekear.certificates_api.web.controllers;

import com.github.nekear.certificates_api.web.dtos.tags.TagCreationRequest;
import com.github.nekear.certificates_api.web.entities.Tag;
import com.github.nekear.certificates_api.web.services.TagsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagsController {
    private final TagsService tagsService;

    @GetMapping
    public List<Tag> getTags() {
        return tagsService.getAll();
    }

    @GetMapping("/{id}")
    public Tag getById(@PathVariable long id) {
        return tagsService.getById(id);
    }

    @PostMapping
    public Tag create(@RequestBody TagCreationRequest tag) {
        return tagsService.create(tag);
    }
}
