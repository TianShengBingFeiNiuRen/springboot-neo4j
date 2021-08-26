package com.andon.springbootneo4j;

import com.andon.springbootneo4j.util.Neo4jUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author Andon
 * 2021/7/29
 */
@Slf4j
public class Demo {

    @Test
    public void test02(){
        String cql = "MATCH (n) RETURN COUNT(n)";
        String response = Neo4jUtil.cqlToNeo4j(cql);
        log.info("response:{}", response);
    }

    @Test
    public void test01() {
        List<String> list = Arrays.asList("Hello,World".split(","));
        String join = String.join(",", list);
        log.info("join:{}", join);
    }
}
