package com.andon.springbootneo4j.dao;

import com.andon.springbootneo4j.model.NodeAddress2;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Andon
 * 2021/8/23
 */
@Repository
public interface NodeAddressRepository2 extends Neo4jRepository<NodeAddress2, Long> {

    @Query(value = "MATCH data=(a:Address)-[t]->(a2:Address) " +
            "WHERE a.hash=$hash AND t.coin=$coin AND t.amount>=$amount AND t.timestamp>=$timestampStart AND t.timestamp<=$timestampEnd " +
            "RETURN COUNT(DISTINCT a.hash) + COUNT(DISTINCT a2.hash)")
    int matchAddressLayer1Count(String hash, String coin, double amount, long timestampStart, long timestampEnd);

    @Query(value = "MATCH data=(a)-[t]->(a2)-[t2]->(a3) " +
            "WHERE a.hash=$hash " +
            "RETURN COUNT(a2.hash) + COUNT(a3.hash)")
    int matchAddressLayer2Count(String hash);

    @Query(value = "MATCH data=(a)-[t]->(a2)-[t2]->(a3)-[t3]->(a4) " +
            "WHERE a.hash=$hash " +
            "RETURN COUNT(a2.hash) + COUNT(a3.hash) + COUNT(a4.hash)")
    int matchAddressLayer3Count(String hash);

    @Query(value = "MATCH data=(a)-[t]->(a2) " +
            "WHERE a.hash=$hash AND a2.tags IS NOT NULL " +
            "RETURN COUNT(a2.hash)")
    int matchAddressAndTagLayer1Count(String hash);

    @Query(value = "MATCH data=(a)-[t]->(a2)-[t2]->(a3) " +
            "WHERE a.hash=$hash AND (a2.tags IS NOT NULL OR a3.tags IS NOT NULL) " +
            "RETURN COUNT(a2.hash) + COUNT(a3.hash)")
    int matchAddressAndTagLayer2Count(String hash);

    @Query(value = "MATCH data=(a)-[t]->(a2)-[t2]->(a3)-[t3]->(a4) " +
            "WHERE a.hash=$hash AND (a2.tags IS NOT NULL OR a3.tags IS NOT NULL OR a4.tags IS NOT NULL) " +
            "RETURN COUNT(a2.hash) + COUNT(a3.hash) + COUNT(a4.hash)")
    int matchAddressAndTagLayer3Count(String hash);
}
