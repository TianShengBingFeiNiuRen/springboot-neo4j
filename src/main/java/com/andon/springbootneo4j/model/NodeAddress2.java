package com.andon.springbootneo4j.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.io.Serializable;

/**
 * @author Andon
 * 2021/8/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node("Address")
public class NodeAddress2 implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Property("hash")
    private String hash;

    @Property("tags")
    private String tags;

//    @Relationship(type = "ERC20TRADE", direction = Relationship.Direction.OUTGOING)
//    private RelationTrade erc20Trade;
//
//    @Relationship(type = "ETHTRADE", direction = Relationship.Direction.OUTGOING)
//    private RelationTrade eth20Trade;
}
