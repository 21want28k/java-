import bean.MyTrigger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import utils.DateUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * UserController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十月 1, 2018</pre>
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:/spring/spring-context.xml", "classpath:/spring/spring-mvc.xml"})
@TestExecutionListeners(listeners = {
        DependencyInjectionTestExecutionListener.class})
@Transactional
@Rollback
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void before() throws Exception {
        scheduler.TaskScheduler scheduler = wac.getBean(scheduler.TaskScheduler.class);
        scheduler.init();
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: login(@Valid User user2, Model model, Errors errors)
     */
    @Test
    public void testAdd() throws Exception {
        MockHttpServletRequestBuilder builder = post("/add");
        builder.param("cron", "* * * * * ?");
        builder.param("jobName", "123456");
        builder.param("jobGroup", "123456");
        builder.param("jobClassName", "scheduler.Job1");
        builder.param("name", "trigger");
        builder.param("group", "triggers");
        builder.param("startTime", "2018-10-30 16:48:00");
        builder.param("endTime", "2018-10-30 16:49:00");
        mockMvc.perform(builder).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testUpdate() throws Exception {
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime startTime = time.plusHours(1);
        LocalDateTime endTime = time.plusHours(2);

        MyTrigger oldTrigger = new MyTrigger("trigger", "triggers", "", null, null, "");
        MyTrigger newTrigger = new MyTrigger("trigger2", "triggers", "", DateUtils.asDate(startTime), DateUtils.asDate(endTime), "* * * * * ?");

        List<MyTrigger> list = new ArrayList<>();
        list.add(oldTrigger);
        list.add(newTrigger);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(list);

        MockHttpServletRequestBuilder builder = post("/update");
        builder.contentType(MediaType.APPLICATION_JSON_UTF8).content(json);
        builder.param("jobName", "123456");
        builder.param("jobGroup", "123456");
        builder.param("jobClassName", "scheduler.Job1");

        mockMvc.perform(builder).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testAllJob() throws Exception {
        MockHttpServletRequestBuilder builder = post("/getAll");
        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }
}