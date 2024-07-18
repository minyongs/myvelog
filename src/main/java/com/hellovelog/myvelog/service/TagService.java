package com.hellovelog.myvelog.service;

import com.hellovelog.myvelog.dto.TagCountDTO;
import com.hellovelog.myvelog.repository.TagsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagsRepository tagsRepository;

    public List<TagCountDTO> findAllWithPostCount() {
        return tagsRepository.findAllWithPostCount();
    }
}
