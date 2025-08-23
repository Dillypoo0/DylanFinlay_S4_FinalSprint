package Tests;
import com.example.bst.service.BstService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BstServiceTest {
    private final BstService svc = new BstService();

    @Test
    void parseNumbersWorks() {
        int[] nums = svc.parseNumbers("7, 3 9;1|5");
        assertArrayEquals(new int[]{7,3,9,1,5}, nums);
    }

    @Test
    void buildBstSequentialProducesSearchOrder() {
        var root = svc.buildBstSequential(new int[]{7,3,9,1,5});
        // root should be 7, left 3, right 9
        assertNotNull(root);
        assertEquals(7, root.val);
        assertEquals(3, root.left.val);
        assertEquals(9, root.right.val);
    }

    @Test
    void balancedTreeIsHeightBalanced() {
        var root = svc.buildBalanced(new int[]{1,2,3,4,5,6,7});
        // quick height check
        assertTrue(Math.abs(height(root.left) - height(root.right)) <= 1);
    }

    private int height(BstService.Node n) {
        if (n == null) return 0;
        return 1 + Math.max(height(n.left), height(n.right));
    }
}
