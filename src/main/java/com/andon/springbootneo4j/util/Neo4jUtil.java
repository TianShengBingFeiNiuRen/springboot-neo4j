package com.andon.springbootneo4j.util;

import org.neo4j.driver.internal.value.PathValue;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;
import org.neo4j.driver.types.Relationship;
import org.springframework.util.ObjectUtils;
import sun.misc.BASE64Encoder;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andon
 * 2021/8/3
 */
@SuppressWarnings("DuplicatedCode")
public class Neo4jUtil {

    public static String url = "http://####:7474/db/data/transaction/commit"; //ip,port

    // rest调用neo4j
    public static String cqlToNeo4j(String cql) {
        String response = null;
        try {
            Map<String, String> header = new HashMap<>();
            Map<String, Object> param = new HashMap<>();

            BASE64Encoder encoder = new BASE64Encoder();
            String text = "###:###"; //"用户名:密码"
            byte[] textByte = text.getBytes(StandardCharsets.UTF_8);
            String encodedText = encoder.encode(textByte);
            header.put("Authorization", "Basic " + encodedText);

            List<Map<String, String>> statements = new ArrayList<>();
            Map<String, String> statement = new HashMap<>();
            statement.put("statement", cql);
            statements.add(statement);
            param.put("statements", statements);

            response = HttpClientUtil.doPostJson(url, header, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 解析 PathValue 转节点
     */
    public static <T> List<Map<String, Object>> getNodeList(List<Object> pathValueList, Class<T> tClass) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, List<Map<String, Object>>> listMap = new HashMap<>();
        Map<String, List<String>> idListMap = new HashMap<>();
        List<String> nameFieldList = new ArrayList<>();
        for (Field declaredField : tClass.getDeclaredFields()) {
            nameFieldList.add(declaredField.getName());
        }
        pathValueList.forEach((o) -> {
            PathValue pathValue = (PathValue) o;
            Path segments = pathValue.asPath();
            Iterable<Node> nodes = segments.nodes();
            nodes.forEach((node) -> {
                List<String> labelList = new ArrayList<>();
                Iterable<String> labels = node.labels();
                labels.forEach(labelList::add);
                String nodeLabel = String.join(",", labelList);
                List<Map<String, Object>> maps = listMap.get(nodeLabel);
                if (ObjectUtils.isEmpty(maps))
                    maps = new ArrayList<>();
                List<String> idList = idListMap.get(nodeLabel);
                if (ObjectUtils.isEmpty(idList))
                    idList = new ArrayList<>();
                String id = String.valueOf(node.id());
                if (!idList.contains(id)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("nodeId", node.id());
                    nameFieldList.forEach((nameField) -> {
                        if (node.containsKey(nameField))
                            map.put(nameField, node.get(nameField).asObject());
                    });
                    map.put("nodeLabel", nodeLabel);
                    maps.add(map);
                    idList.add(id);
                }
                listMap.put(nodeLabel, maps);
                idListMap.put(nodeLabel, idList);
            });
        });
        listMap.forEach((key, value) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("nodeLabel", key);
            map.put("nodeList", value);
            mapList.add(map);
        });
        return mapList;
    }

    /**
     * 解析 PathValue 转关系
     */
    public static <T> List<Map<String, Object>> getRelationList(List<Object> pathValueList, Class<T> tClass) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, List<Map<String, Object>>> listMap = new HashMap<>();
        Map<String, List<String>> idListMap = new HashMap<>();
        List<String> nameFieldList = new ArrayList<>();
        for (Field declaredField : tClass.getDeclaredFields()) {
            nameFieldList.add(declaredField.getName());
        }
        pathValueList.forEach((o) -> {
            PathValue pathValue = (PathValue) o;
            Path segments = pathValue.asPath();
            Iterable<Relationship> relationships = segments.relationships();
            relationships.forEach((relationship) -> {
                String relationshipType = relationship.type();
                List<Map<String, Object>> maps = listMap.get(relationshipType);
                if (ObjectUtils.isEmpty(maps))
                    maps = new ArrayList<>();
                List<String> idList = idListMap.get(relationshipType);
                if (ObjectUtils.isEmpty(idList))
                    idList = new ArrayList<>();
                String id = String.valueOf(relationship.id());
                if (!idList.contains(id)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("relationshipId", relationship.id());
                    map.put("relationshipType", relationship.type());
                    map.put("startNodeId", relationship.startNodeId());
                    map.put("endNodeId", relationship.endNodeId());
                    nameFieldList.forEach((nameField) -> {
                        if (relationship.containsKey(nameField))
                            map.put(nameField, relationship.get(nameField).asObject());
                    });
                    maps.add(map);
                    idList.add(id);
                }
                listMap.put(relationshipType, maps);
                idListMap.put(relationshipType, idList);
            });
        });
        listMap.forEach((key, value) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("relationshipType", key);
            map.put("relationshipList", value);
            mapList.add(map);
        });
        return mapList;
    }
}
