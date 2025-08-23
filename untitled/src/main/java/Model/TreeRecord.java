package Model;

import jakarta.persistance.*;
import java.time. Instant;

@Entity
public class TreeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String inputNumbersCsv; // what user entered (normalized as CSV)

    @Lob
    private String treeJson; // serialized tree structure

    private boolean balanced;

    private Instant createdAt = Instant.now();

    public TreeRecord() {}

    public TreeRecord(String inputNumbersCsv, String treeJson, boolean balanced) {
        this.inputNumbersCsv = inputNumbersCsv;
        this.treeJson = treeJson;
        this.balanced = balanced;
    }

    public Long getId() { return id; }
    public String getInputNumbersCsv() { return inputNumbersCsv; }
    public String getTreeJson() { return treeJson; }
    public boolean isBalanced() { return balanced; }
    public Instant getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setInputNumbersCsv(String inputNumbersCsv) { this.inputNumbersCsv = inputNumbersCsv; }
    public void setTreeJson(String treeJson) { this.treeJson = treeJson; }
    public void setBalanced(boolean balanced) { this.balanced = balanced; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
