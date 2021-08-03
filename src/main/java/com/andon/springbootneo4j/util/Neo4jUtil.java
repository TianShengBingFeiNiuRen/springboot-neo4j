package com.andon.springbootneo4j.util;

import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.PathValue;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;
import org.neo4j.driver.types.Relationship;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andon
 * 2021/8/3
 */
public class Neo4jUtil {

    /**
     * 解析 PathValue 转节点
     */
    public static <T> List<Map<String, Object>> getNodeList(List<Object> pathValueList, Class<T> tClass) {
        List<Map<String, Object>> maps = new ArrayList<>();
        List<String> name_fields = new ArrayList<>();
        for (Field declaredField : tClass.getDeclaredFields()) {
            name_fields.add(declaredField.getName());
        }
        pathValueList.forEach((o) -> {
            PathValue pathValue = (PathValue) o;
            Path segments = pathValue.asPath();
            Iterable<Node> nodes = segments.nodes();
            nodes.forEach((node) -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id_node", node.id());
                name_fields.forEach((name_field) -> {
                    Value value = node.get(name_field);
                    if (node.containsKey(name_field))
                        map.put(name_field, node.get(name_field).toString());
                });
                List<String> label_list = new ArrayList<>();
                Iterable<String> labels = node.labels();
                labels.forEach(label_list::add);
                map.put("label_list", label_list);
                maps.add(map);
            });
        });
        return maps;
    }

    /**
     * 解析 PathValue 转关系
     */
    public static <T> List<Map<String, Object>> getRelationList(List<Object> pathValueList, Class<T> tClass) {
        List<Map<String, Object>> maps = new ArrayList<>();
        List<String> name_fields = new ArrayList<>();
        for (Field declaredField : tClass.getDeclaredFields()) {
            name_fields.add(declaredField.getName());
        }
        pathValueList.forEach((o) -> {
            PathValue pathValue = (PathValue) o;
            Path segments = pathValue.asPath();
            Iterable<Relationship> relationships = segments.relationships();
            relationships.forEach((relationship) -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id_relationship", relationship.id());
                map.put("type_relationship", relationship.type());
                map.put("startNodeId", relationship.startNodeId());
                map.put("endNodeId", relationship.endNodeId());
                name_fields.forEach((name_field) -> {
                    if (relationship.containsKey(name_field))
                        map.put(name_field, relationship.get(name_field).toString());
                });
                maps.add(map);
            });
        });
        return maps;
    }
}
