package com.github.nekear.certificates_api.web.services;

import com.github.nekear.certificates_api.web.exceptions.entities.FlowException;
import com.github.nekear.certificates_api.web.dtos.tags.TagCreationRequest;
import com.github.nekear.certificates_api.web.entities.Tag;
import com.github.nekear.certificates_api.web.repos.daos.prototypes.TagsDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagsService {
    private final TagsDAO tagsDAO;

    public Tag getById(long id){
        return tagsDAO.findById(id).orElseThrow(() -> new FlowException("Tag not found", HttpStatus.NOT_FOUND));
    }

    public List<Tag> getAll(){
        return tagsDAO.findAll();
    }

    public Tag create(TagCreationRequest tag){
        var newTag = Tag.builder().name(tag.getName()).build();
        newTag.setId(tagsDAO.createOne(newTag));

        return newTag;
    }
}
