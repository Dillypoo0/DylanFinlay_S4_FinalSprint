package com.bstapp.controller;

import com.bstapp.model.TreeRecord;
import com.bstapp.repo.TreeRecordRepository;
import com.bstapp.service.BstService;
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

    @PostMapping("/process-numbers")
    public Map<String, Object> process(@RequestParam String numbers,
                                       @RequestParam(defaultValue = "false") boolean balanced) {

        int[] parsed = bstService.parseNumbers(numbers);
        var root = balanced ? bstService.buildBalanced(parsed)
                            : bstService.buildBstSequential(parsed);

        String treeJson = bstService.toJson(root);
        String numbersCsv = bstService.normalizeCsv(parsed);

        TreeRecord saved = repo.save(new TreeRecord(numbersCsv, treeJson, balanced));

        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("id", saved.getId());
        resp.put("balanced", saved.isBalanced());
        resp.put("numbersCsv", saved.getNumbersCsv());
        resp.put("tree", treeJson);
        return resp;
    }

    @GetMapping("/previous-trees")
    public List<Map<String, Object>> previous() {
        return repo.findAll().stream()
                .sorted(Comparator.comparing(TreeRecord::getCreatedAt).reversed())
                .map(tr -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("id", tr.getId());
                    m.put("createdAt", tr.getCreatedAt().toString());
                    m.put("balanced", tr.isBalanced());
                    m.put("numbersCsv", tr.getNumbersCsv());
                    m.put("tree", tr.getTreeJson());
                    return m;
                }).toList();
    }
}
