package com.lyflexi.feignx.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: lyflexi
 * @project: feignx-plugin
 * @Date: 2024/10/18 14:54
 */
public enum SpringBootMethodAnnotation {
    /**
     * RequestMapping
     */
    REQUEST_MAPPING("org.springframework.web.bind.annotation.RequestMapping", null),
    /**
     * GetMapping
     */
    GET_MAPPING("org.springframework.web.bind.annotation.GetMapping", "GET"),
    /**
     * PostMapping
     */
    POST_MAPPING("org.springframework.web.bind.annotation.PostMapping", "POST"),
    /**
     * PutMapping
     */
    PUT_MAPPING("org.springframework.web.bind.annotation.PutMapping", "PUT"),
    /**
     * DeleteMapping
     */
    DELETE_MAPPING("org.springframework.web.bind.annotation.DeleteMapping", "DELETE"),
    /**
     * PatchMapping
     */
    PATCH_MAPPING("org.springframework.web.bind.annotation.PatchMapping", "PATCH");

    private final String qualifiedName;
    private final String methodName;

    SpringBootMethodAnnotation(String qualifiedName, String methodName) {
        this.qualifiedName = qualifiedName;
        this.methodName = methodName;
    }

    public static SpringBootMethodAnnotation getByQualifiedName(String qualifiedName) {
        for (SpringBootMethodAnnotation springRequestAnnotation : SpringBootMethodAnnotation.values()) {
            if (springRequestAnnotation.getQualifiedName().equals(qualifiedName)) {
                return springRequestAnnotation;
            }
        }
        return null;
    }


    public String methodName() {
        return this.methodName;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public String getShortName() {
        return qualifiedName.substring(qualifiedName.lastIndexOf(".") - 1);
    }

    /**
     * 获取所有的keys
     *             "org.springframework.web.bind.annotation.RequestMapping",
     *             "org.springframework.web.bind.annotation.GetMapping",
     *             "org.springframework.web.bind.annotation.PostMapping",
     *             "org.springframework.web.bind.annotation.PutMapping",
     *             "org.springframework.web.bind.annotation.DeleteMapping",
     *             "org.springframework.web.bind.annotation.PatchMapping"
     * @return
     */
    public static List<String> allQualifiedNames() {
        return Arrays.stream(SpringBootMethodAnnotation.values()).map(SpringBootMethodAnnotation::getQualifiedName).collect(Collectors.toList());
    }
}