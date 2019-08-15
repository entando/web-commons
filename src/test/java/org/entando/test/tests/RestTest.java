package org.entando.test.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestTest {

    @Autowired private MockMvc mockMvc;

    @Test
    public void testRequestList() throws Exception {
        mockMvc.perform(get("/test"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("payload", hasSize(2)))
                .andExpect(jsonPath("payload[0].name").value("John"))
                .andExpect(jsonPath("payload[0].familyName").value("Doe"))
                .andExpect(jsonPath("payload[0].active").value(false))
                .andExpect(jsonPath("payload[0].age").value(31))

                .andExpect(jsonPath("payload[1].name").value("Ryan"))
                .andExpect(jsonPath("payload[1].familyName").value("Reynolds"))
                .andExpect(jsonPath("payload[1].active").value(true))
                .andExpect(jsonPath("payload[1].age").value(35))

                .andExpect(jsonPath("metadata.page").value(1))
                .andExpect(jsonPath("metadata.pageSize").value(100))
                .andExpect(jsonPath("metadata.lastPage").value(1))
                .andExpect(jsonPath("metadata.totalItems").value(2))
                .andExpect(jsonPath("metadata.sort").value("id"))
                .andExpect(jsonPath("metadata.direction").value("ASC"))
                .andExpect(jsonPath("metadata.filters", hasSize(0)));
    }

    @Test
    public void testRequestListWithFilters() throws Exception {
        mockMvc.perform(get("/test?filters[0].attribute=name&filters[0].operator=eq&&filters[0].value=John"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("payload", hasSize(1)))
                .andExpect(jsonPath("payload[0].name").value("John"))
                .andExpect(jsonPath("payload[0].familyName").value("Doe"))
                .andExpect(jsonPath("payload[0].active").value(false))
                .andExpect(jsonPath("payload[0].age").value(31))

                .andExpect(jsonPath("metadata.page").value(1))
                .andExpect(jsonPath("metadata.pageSize").value(100))
                .andExpect(jsonPath("metadata.lastPage").value(1))
                .andExpect(jsonPath("metadata.totalItems").value(1))
                .andExpect(jsonPath("metadata.sort").value("id"))
                .andExpect(jsonPath("metadata.direction").value("ASC"))
                .andExpect(jsonPath("metadata.filters", hasSize(1)))
                .andExpect(jsonPath("metadata.filters[0].attribute").value("name"))
                .andExpect(jsonPath("metadata.filters[0].operator").value("eq"))
                .andExpect(jsonPath("metadata.filters[0].value").value("John"));
    }

    @Test
    public void testRequestListWithSort() throws Exception {
        mockMvc.perform(get("/test?sort=name&direction=DESC"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("payload", hasSize(2)))
                .andExpect(jsonPath("payload[0].name").value("Ryan"))
                .andExpect(jsonPath("payload[1].name").value("John"))
                .andExpect(jsonPath("metadata.filters", hasSize(0)))
                .andExpect(jsonPath("metadata.sort").value("name"))
                .andExpect(jsonPath("metadata.direction").value("DESC"));
    }

    @Test
    public void testSimpleRestResponse() throws Exception {
        mockMvc.perform(get("/test/single"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("payload.name").value("John"))
                .andExpect(jsonPath("payload.familyName").value("Doe"))
                .andExpect(jsonPath("payload.active").value(false))
                .andExpect(jsonPath("payload.age").value(31));
    }
}
