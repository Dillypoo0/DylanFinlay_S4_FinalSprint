package Controller;

package com.example.bst.controller;

import com.example.bst.model.TreeRecord;
import com.example.bst.repo.TreeRecordRepository;
import com.example.bst.service.BstService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TreeApiController {

    private final BstService bstService;
    private final TreeRecordRepository repo;

    public TreeApiController(BstService bstService, TreeRecordRepository repo) {
        this.bstService = bstService;
        this.repo = repo;
    }

    // POST /process-numbers
    // Form or JSON:
    // - form fields: numbers="7,3,9,1,5" & balanced=true|false
    // - or JSON: { "numbers": "7,3,9,1,5", "balanced": true }
    @PostMapping(value = "/process-numbers", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> process(@RequestParam(required = false) String numbers,
                                       @RequestParam(required = false, defaultValue = "false") boolean balanced,
                                       @RequestBody(required = false) Map<String, Object> body) {

        if ((numbers == null || numbers.isBlank()) && body != null) {
            Object n = body.get("numbers");
            if (n != null) numbers = n.toString();
            Object b = body.get("balanced");
            if (b != null) balanced = Boolean.parseBoolean(b.toString());
        }

        int[] parsed = bstService.parseNumbers(numbers);
        BstService.Node root = balanced
                ? bstService.buildBalanced(parsed)
                : bstService.buildBstSequential(parsed);

        String json = bstService.toJson(root);
        String csv = bstService.normalizeCsv(parsed);

        TreeRecord saved = repo.save(new TreeRecord(csv, json, balanced));

        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("id", saved.getId());
        resp.put("balanced", saved.isBalanced());
        resp.put("inputCsv", saved.getInputNumbersCsv());
        resp.put("tree", json); // already a JSON string; client can JSON.parse
        return resp;
    }

    // GET /previous-trees
    @GetMapping("/previous-trees")
    public List<Map<String, Object>> previous() {
        return repo.findAll().stream().sorted(Comparator.comparing(TreeRecord::getCreatedAt).reversed())
                .map(tr -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("id", tr.getId());
                    m.put("createdAt", tr.getCreatedAt().toString());
                    m.put("balanced", tr.isBalanced());
                    m.put("inputCsv", tr.getInputNumbersCsv());
                    m.put("tree", tr.getTreeJson());
                    return m;
                }).toList();
    }
}
