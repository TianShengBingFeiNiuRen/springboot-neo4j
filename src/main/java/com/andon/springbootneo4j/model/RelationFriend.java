package com.andon.springbootneo4j.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.io.Serializable;
import java.util.List;

/**
 * @author Andon
 * 2021/8/2
 */
@Builder
@Data
@RelationshipProperties
public class RelationFriend implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private List<NodePerson> personList;

    private String value;
}
