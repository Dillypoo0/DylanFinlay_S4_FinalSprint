package com.bstapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BstService {

    public static class Node {
        public int val;
        public Node left, right;
        public Node(int v) { this.val = v; }
    }

    public int[] parseNumbers(String raw) {
        if (raw == null || raw.isBlank()) return new int[0];
        String norm = raw.replaceAll("[,;|]", " ");
        return Arrays.stream(norm.trim().split("\s+"))
                     .filter(p -> !p.isBlank())
                     .mapToInt(Integer::parseInt)
                     .toArray();
    }

    public Node insert(Node root, int v) {
        if (root == null) return new Node(v);
        if (v < root.val) root.left = insert(root.left, v);
        else root.right = insert(root.right, v);
        return root;
    }

    public Node buildBstSequential(int[] nums) {
        Node root = null;
        for (int n : nums) root = insert(root, n);
        return root;
    }

    public Node buildBalanced(int[] nums) {
        int[] unique = Arrays.stream(nums).sorted().distinct().toArray();
        return buildBalanced(unique, 0, unique.length - 1);
    }
    private Node buildBalanced(int[] arr, int lo, int hi) {
        if (lo > hi) return null;
        int mid = (lo + hi) >>> 1;
        Node root = new Node(arr[mid]);
        root.left = buildBalanced(arr, lo, mid - 1);
        root.right = buildBalanced(arr, mid + 1, hi);
        return root;
    }

    public String toJson(Node root) {
        Map<String, Object> m = toMap(root);
        try { return new ObjectMapper().writeValueAsString(m); }
        catch (JsonProcessingException e) { throw new RuntimeException(e); }
    }
    private Map<String, Object> toMap(Node n) {
        if (n == null) return null;
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("val", n.val);
        m.put("left", toMap(n.left));
        m.put("right", toMap(n.right));
        return m;
    }

    public String normalizeCsv(int[] nums) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(nums[i]);
        }
        return sb.toString();
    }
}
