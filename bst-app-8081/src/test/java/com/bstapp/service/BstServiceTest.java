package com.bstapp.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BstServiceTest {
    private final BstService svc = new BstService();

    @Test
    void parseNumbers_mixedDelims() {
        int[] nums = svc.parseNumbers("7, 3 9;1|5");
        assertArrayEquals(new int[]{7,3,9,1,5}, nums);
    }

    @Test
    void buildBstSequential_basicShape() {
        var root = svc.buildBstSequential(new int[]{7,3,9,1,5});
        assertNotNull(root);
        assertEquals(7, root.val);
        assertEquals(3, root.left.val);
        assertEquals(9, root.right.val);
    }

    @Test
    void balancedTree_isRoughlyBalanced() {
        var root = svc.buildBalanced(new int[]{1,2,3,4,5,6,7});
        assertTrue(Math.abs(height(root.left) - height(root.right)) <= 1);
    }

    private int height(BstService.Node n) {
        if (n == null) return 0;
        return 1 + Math.max(height(n.left), height(n.right));
    }
}
