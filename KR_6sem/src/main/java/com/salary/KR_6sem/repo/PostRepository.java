package com.salary.KR_6sem.repo;

import com.salary.KR_6sem.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {

    Optional<Post> findByJob(String job);

}
