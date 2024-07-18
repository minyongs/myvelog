package com.hellovelog.myvelog.repository;

import com.hellovelog.myvelog.domain.Tags;
import com.hellovelog.myvelog.dto.TagCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagsRepository extends JpaRepository<Tags, Long> {
    @Query("SELECT t FROM Tags t WHERE t.name = :name")
    Tags findByName(@Param("name") String name);

    @Query("SELECT new com.hellovelog.myvelog.dto.TagCountDTO(t.name, COUNT(pt)) " +
            "FROM Tags t LEFT JOIN t.postTags pt " +
            "GROUP BY t.name")
    List<TagCountDTO> findAllWithPostCount();
}
