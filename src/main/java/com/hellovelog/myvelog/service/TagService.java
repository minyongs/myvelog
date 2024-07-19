package com.hellovelog.myvelog.service;

import com.hellovelog.myvelog.dto.TagCountDTO;
import com.hellovelog.myvelog.repository.TagsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagsRepository tagsRepository;
    @Transactional
    public List<TagCountDTO> findAllWithPostCount() {
        return tagsRepository.findAllWithPostCount();
    }
}
