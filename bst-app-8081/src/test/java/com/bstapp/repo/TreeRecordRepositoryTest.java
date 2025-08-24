package com.bstapp.repo;

import com.bstapp.model.TreeRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TreeRecordRepositoryTest {

    @Autowired
    private TreeRecordRepository repo;

    @Test
    void saveAndLoad() {
        var rec = new TreeRecord("7,3,9,1,5", "{\"val\":7}", false);
        var saved = repo.save(rec);
        assertNotNull(saved.getId());
        var all = repo.findAll();
        assertEquals(1, all.size());
        assertEquals("7,3,9,1,5", all.get(0).getNumbersCsv());
    }
}
