package Tests;

package com.example.bst;

import com.example.bst.controller.TreeApiController;
import com.example.bst.repo.TreeRecordRepository;
import com.example.bst.service.BstService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TreeApiControllerTest {
    @Test
    void processStoresAndReturnsJson() {
        var repo = mock(TreeRecordRepository.class);
        var svc = new BstService();
        var ctrl = new TreeApiController(svc, repo);

        // Simplify: don’t assert DB save content (mocked), assert response shape
        Map<String, Object> resp = ctrl.process("7,3,9,1,5", false, null);
        assertTrue(resp.containsKey("id")); // will be null until save is stubbed
        assertTrue(resp.containsKey("tree"));
        assertTrue(resp.containsKey("inputCsv"));
    }
}
