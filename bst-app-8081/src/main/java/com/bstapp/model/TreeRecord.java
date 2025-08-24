package com.bstapp.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class TreeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String numbersCsv;

    @Lob
    private String treeJson;

    private boolean balanced;

    private Instant createdAt = Instant.now();

    public TreeRecord() { }

    public TreeRecord(String numbersCsv, String treeJson, boolean balanced) {
        this.numbersCsv = numbersCsv;
        this.treeJson = treeJson;
        this.balanced = balanced;
    }

    public Long getId() { return id; }
    public String getNumbersCsv() { return numbersCsv; }
    public String getTreeJson() { return treeJson; }
    public boolean isBalanced() { return balanced; }
    public Instant getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setNumbersCsv(String numbersCsv) { this.numbersCsv = numbersCsv; }
    public void setTreeJson(String treeJson) { this.treeJson = treeJson; }
    public void setBalanced(boolean balanced) { this.balanced = balanced; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
