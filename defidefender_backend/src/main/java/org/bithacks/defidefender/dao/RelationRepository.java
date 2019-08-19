package org.bithacks.defidefender.dao;

import org.bithacks.defidefender.model.Po.Relation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationRepository extends JpaRepository<Relation, Integer> {
    List<Relation> findRelationsByName(String name);

    List<Relation> findRelationsByType(int type);
}
