package com.andon.springbootneo4j.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.io.Serializable;

/**
 * @author Andon
 * 2021/8/2
 */
@Builder
@Data
@Node("location")
public class NodeLocation implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;
}
