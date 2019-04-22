package rapbattles.rap_battles.Controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
public class testControllerTest {

//    private MockMvc mockMvc;
//
//    @InjectMocks
//    private TestController testController;
//
//    @Before
//    public void setUp(){
//        mockMvc = MockMvcBuilders.standaloneSetup(testController).build();
//    }
//
//    @Test
//    public void testControllerTest() throws Exception {
//        mockMvc.perform(get("/test"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("test"));
//    }
//
//    @Test
//    public void testControllerTestJson() throws Exception{
//        mockMvc.perform(get("/json"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.text", Matchers.is("Testing")))
//                .andExpect(jsonPath("$.text2", Matchers.is(", testing 1,2,3.")))
//                .andExpect(jsonPath("$.*",Matchers.hasSize(2)));
//    }
}