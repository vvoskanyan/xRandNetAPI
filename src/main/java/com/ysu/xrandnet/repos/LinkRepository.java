package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.Link;
import com.ysu.xrandnet.models.LinkType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Integer> {
    Link findById(int id);

    List<Link> findLinkByType(LinkType type);
}
