package controller;

import bean.MyJob;
import bean.MyTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.SchedulerService;

import java.util.List;

@Controller
public class SchedulerController {

    private SchedulerService schedulerService;

    @Autowired
    public SchedulerController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @RequestMapping("/add")
    private String addJob(MyJob job, MyTrigger trigger) {
        schedulerService.addJob(job, trigger);
        return "success";
    }

    @RequestMapping("/delete")
    private String delete(MyJob job) {
        schedulerService.deleteJob(job);
        return "success";
    }

    @RequestMapping("/update")
    private String update(MyJob job, @RequestBody List<MyTrigger> triggers) {
        schedulerService.updateJob(job, triggers.get(0), triggers.get(1));
        return "success";
    }

    @RequestMapping("/getAll")
    @ResponseBody
    private List<MyJob> getJobs() {
        return schedulerService.getAllJob();
    }
}