package com.bstapp.controller;

import com.bstapp.model.TreeRecord;
import com.bstapp.repo.TreeRecordRepository;
import com.bstapp.service.BstService;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TreeApiController.class)
@Import(BstService.class)
class TreeApiControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TreeRecordRepository repo;

    @Test
    void processNumbers_form_returnsStoredJson() throws Exception {
        when(repo.save(any(TreeRecord.class))).thenAnswer((Answer<TreeRecord>) inv -> {
            TreeRecord tr = inv.getArgument(0);
            tr.setId(1L);
            return tr;
        });

        mvc.perform(post("/process-numbers")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("numbers", "7,3,9,1,5")
                .param("balanced", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.numbersCsv").value("7,3,9,1,5"))
                .andExpect(jsonPath("$.balanced").value(false))
                .andExpect(jsonPath("$.tree").exists());
    }
}
