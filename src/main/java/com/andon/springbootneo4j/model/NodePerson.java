package com.andon.springbootneo4j.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.io.Serializable;

/**
 * @author Andon
 * 2021/8/2
 */
@Builder
@Data
@Node("person")
public class NodePerson implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;

    @Relationship(type = "location", direction = Relationship.Direction.OUTGOING)
    private NodeLocation location;

    @Relationship(type = "friend", direction = Relationship.Direction.OUTGOING)
    private RelationFriend friend;
}
