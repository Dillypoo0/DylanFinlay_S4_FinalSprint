package Tests;

import com.example.bst.model.TreeRecord;
import com.example.bst.repo.TreeRecordRepository;
import com.sun.source.tree.Tree;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

public class TreeRecordRepositoryTest {
    @Nested
    @DataJpaTest
    class treeRecordRepositoryTest {

        @Autowired
        private TreeRecordRepository repo;

        @Test
        <TreeRecord>
        void saveAndFind() {
            TreeRecord rec;
            rec = new TreeRecord("7,3,9,1,5", "{\"val\":7}", false);
            var saved = repo.save(rec);
            assertNotNull(saved.getId());
            var all = repo.findAll();
            assertEquals(1, all.size());
            assertEquals("7,3,9,1,5", all.get(0).getInputNumbersCsv());
        }

        private class TreeRecordRepository {
            public <TreeRecord> Tree save(TreeRecord rec) {
                return null;
            }
        }
    }
