package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Integer> {
    Link findById(int id);
}
