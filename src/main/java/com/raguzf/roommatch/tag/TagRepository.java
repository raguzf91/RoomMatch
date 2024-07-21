package com.raguzf.roommatch.tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer>{
    
    Optional<Tag> findByName(String tagName);
}
