package com.andon.springbootneo4j;

import com.alibaba.fastjson.JSONObject;
import com.andon.springbootneo4j.dao.NodeLocationRepository;
import com.andon.springbootneo4j.dao.NodePersonRepository;
import com.andon.springbootneo4j.dao.RelationFriendRepository;
import com.andon.springbootneo4j.model.NodeLocation;
import com.andon.springbootneo4j.model.NodePerson;
import com.andon.springbootneo4j.model.RelationFriend;
import com.andon.springbootneo4j.util.Neo4jUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Andon
 * 2021/7/26
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseTest {

    @Resource
    private NodePersonRepository nodePersonRepository;
    @Resource
    private NodeLocationRepository nodeLocationRepository;
    @Resource
    private RelationFriendRepository relationFriendRepository;

    @Test
    public void test07() {
        List<Object> test = relationFriendRepository.test();
        List<Map<String, Object>> nodeLocationList = Neo4jUtil.getNodeList(test, NodeLocation.class);
        List<Map<String, Object>> nodePersonList = Neo4jUtil.getNodeList(test, NodePerson.class);
        List<Map<String, Object>> relationFriendMapList = Neo4jUtil.getRelationList(test, RelationFriend.class);
        log.info("nodeLocationList:{}", JSONObject.toJSONString(nodeLocationList));
        log.info("nodePersonList:{}", JSONObject.toJSONString(nodePersonList));
        log.info("relationFriendMapList:{}", JSONObject.toJSONString(relationFriendMapList));
    }

    @Test
    public void test06() {
        String value = "桃园三结义";
        List<Object> relationFriends = relationFriendRepository.matchFriendWhereValue(value);
        List<Map<String, Object>> nodeList = Neo4jUtil.getNodeList(relationFriends, NodePerson.class);
        List<Map<String, Object>> relationList = Neo4jUtil.getRelationList(relationFriends, RelationFriend.class);
        log.info("nodeList:{}", JSONObject.toJSONString(nodeList));
        log.info("relationList:{}", JSONObject.toJSONString(relationList));
    }

    @Test
    public void test05() {
        String value = "桃园三结义";
        List<Object> relationFriends = relationFriendRepository.matchFriendWhereValue(value);
        String nameLocation = "蜀国";
        List<NodeLocation> nodeLocationList = nodeLocationRepository.matchLocationWhereName(nameLocation);
        String namePerson = "诸葛亮";
        List<NodePerson> nodePersonList = nodePersonRepository.matchPersonWhereName(namePerson);
        for (Object relationFriend : relationFriends) {
            log.info("relationFriend:{}", relationFriend.toString());
        }
        log.info("nodeLocationList:{}", JSONObject.toJSONString(nodeLocationList));
        log.info("nodePersonList:{}", JSONObject.toJSONString(nodePersonList));
    }

    @Test
    public void test04() {
        List<NodePerson> personList = new ArrayList<>();
        String names = "黄忠,马超,赵云";
        for (String name : names.split(",")) {
            NodePerson person = NodePerson.builder().name(name).build();
            personList.add(person);
        }
        List<NodePerson> nodePersonList = nodePersonRepository.saveAll(personList);
        log.info("nodePersonList:{}", JSONObject.toJSONString(nodePersonList));
    }

    @Test
    public void test03() {
        NodeLocation location = NodeLocation.builder().id(25L).name("蜀国").build();
        List<NodePerson> targetNodeList = new ArrayList<>();
        NodePerson targetNode = NodePerson.builder().id(27L).name("张飞").build();
        targetNodeList.add(targetNode);
        RelationFriend friend = RelationFriend.builder().value("桃园三结义").personList(targetNodeList).build();
        NodePerson person = NodePerson.builder().id(26L).name("关羽").location(location).friend(friend).build();
        NodePerson save = nodePersonRepository.save(person);
        log.info("person:{}", JSONObject.toJSONString(person));
        log.info("save:{}", JSONObject.toJSONString(save));
    }

    @Test
    public void test02() {
        NodeLocation location = NodeLocation.builder().name("蜀国").build();
        NodeLocation save = nodeLocationRepository.save(location);
        log.info("location:{}", JSONObject.toJSONString(location));
        log.info("save:{}", JSONObject.toJSONString(save));
    }

    @Test
    public void test01() {
        NodePerson person = NodePerson.builder().name("诸葛亮").build();
        NodePerson save = nodePersonRepository.save(person);
        log.info("person:{}", JSONObject.toJSONString(person));
        log.info("save:{}", JSONObject.toJSONString(save));
    }
}
