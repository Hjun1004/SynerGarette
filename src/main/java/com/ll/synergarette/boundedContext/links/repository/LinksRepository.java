package com.ll.synergarette.boundedContext.links.repository;

import com.ll.synergarette.boundedContext.links.entity.Links;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinksRepository extends JpaRepository<Links, Long> {
}
