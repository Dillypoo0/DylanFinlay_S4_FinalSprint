package Service;
package com.example.bst.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BstService {

    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node(int v) { this.val = v; }
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

    // BONUS: build a balanced BST from the same values (sorted unique)
    public Node buildBalanced(int[] nums) {
        int[] sorted = Arrays.stream(nums).sorted().toArray();
        // Keep duplicates to reflect original values? Often BSTs ignore duplicates.
        // Here we keep duplicates but they’ll skew balance a bit, so we unique() for true balance:
        int[] unique = Arrays.stream(sorted).distinct().toArray();
        return buildBalancedFromSorted(unique, 0, unique.length - 1);
    }

    private Node buildBalancedFromSorted(int[] arr, int lo, int hi) {
        if (lo > hi) return null;
        int mid = (lo + hi) >>> 1;
        Node root = new Node(arr[mid]);
        root.left = buildBalancedFromSorted(arr, lo, mid - 1);
        root.right = buildBalancedFromSorted(arr, mid + 1, hi);
        return root;
    }

    // Serialize tree to a nested JSON object
    public String toJson(Node root) {
        Map<String, Object> m = toMap(root);
        try {
            return new ObjectMapper().writeValueAsString(m);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> toMap(Node n) {
        if (n == null) return null;
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("val", n.val);
        m.put("left", toMap(n.left));
        m.put("right", toMap(n.right));
        return m;
    }

    // Parse input like "7, 3 9;1|5"
    public int[] parseNumbers(String raw) {
        if (raw == null || raw.isBlank()) return new int[0];
        String norm = raw.replaceAll("[;|]", " ").replaceAll(",", " ");
        String[] parts = norm.trim().split("\\s+");
        return Arrays.stream(parts)
                .filter(p -> !p.isBlank())
                .mapToInt(Integer::parseInt)
                .toArray();
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
