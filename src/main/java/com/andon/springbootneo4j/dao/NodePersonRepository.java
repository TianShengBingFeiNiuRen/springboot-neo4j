package com.andon.springbootneo4j.dao;

import com.andon.springbootneo4j.model.NodePerson;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Andon
 * 2021/8/2
 */
@Repository
public interface NodePersonRepository extends Neo4jRepository<NodePerson, Long> {

    @Query(value = "MATCH (n:person) WHERE n.name=$name RETURN n")
    List<NodePerson> matchPersonWhereName(String name);
}
