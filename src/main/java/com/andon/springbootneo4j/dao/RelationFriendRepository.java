package com.andon.springbootneo4j.dao;

import com.andon.springbootneo4j.model.RelationFriend;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Andon
 * 2021/8/2
 */
@Repository
public interface RelationFriendRepository extends Neo4jRepository<RelationFriend, Long> {

    @Query(value = "MATCH relation=(p1:person)-[r:friend]->(p2:person) WHERE r.value=$value RETURN relation")
    List<Object> matchFriendWhereValue(String value);
}
