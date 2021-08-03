package com.andon.springbootneo4j.dao;

import com.andon.springbootneo4j.model.NodeLocation;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Andon
 * 2021/8/2
 */
@Repository
public interface NodeLocationRepository extends Neo4jRepository<NodeLocation, Long> {

    @Query(value = "MATCH (n:location) WHERE n.name=$name RETURN n")
    List<NodeLocation> matchLocationWhereName(String name);
}
