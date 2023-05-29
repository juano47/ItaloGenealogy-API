package com.delaiglesia.italogenealogy.repository;

import com.delaiglesia.italogenealogy.domain.Holder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Holder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HolderRepository extends JpaRepository<Holder, Long> {}
