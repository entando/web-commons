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
public class SwaggerTest {

    @Autowired private MockMvc mockMvc;

    @Test
    public void testSwagger() throws Exception {
        mockMvc.perform(get("/v2/api-docs"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("swagger").value("2.0"))
                .andExpect(jsonPath("info.title").value("Entando Test"))
                .andExpect(jsonPath("info.description").value("Entando Mock Test"))
                .andExpect(jsonPath("info.version").value("1.2.3"))
                .andExpect(jsonPath("info.termsOfService").value("https://github.com/entando/entando-core/wiki"))
                .andExpect(jsonPath("info.contact.name").value("Sergio Marcelino"))
                .andExpect(jsonPath("info.contact.email").value("s.marcelino@entando.com"))
                .andExpect(jsonPath("info.license.name").value("GNU Lesser General Public License v2.1"))
                .andExpect(jsonPath("info.license.url").value("https://www.gnu.org/licenses/lgpl-2.1.txt"))
                .andExpect(jsonPath("host").value("localhost"))
                .andExpect(jsonPath("basePath").value("/"))

                .andExpect(jsonPath("tags", hasSize(1)))
                .andExpect(jsonPath("tags[0].name").value("test-mock-controller"))
                .andExpect(jsonPath("tags[0].description").value("Test Mock Controller"))

                .andExpect(jsonPath("paths[\"/test\"]").isMap())
                .andExpect(jsonPath("paths[\"/test\"].get").isMap())
                .andExpect(jsonPath("paths[\"/test\"].get.tags").isArray())
                .andExpect(jsonPath("paths[\"/test\"].get.tags", hasSize(1)))
                .andExpect(jsonPath("paths[\"/test\"].get.tags[0]").value("test-mock-controller"))
                .andExpect(jsonPath("paths[\"/test\"].get.summary").value("list"))
                .andExpect(jsonPath("paths[\"/test\"].get.operationId").value("listUsingGET"))
                .andExpect(jsonPath("paths[\"/test\"].get.produces", hasSize(1)))
                .andExpect(jsonPath("paths[\"/test\"].get.produces[0]").value("application/json"));
    }

}
