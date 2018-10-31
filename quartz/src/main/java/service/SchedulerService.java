package service;

import bean.MyJob;
import bean.MyTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scheduler.TaskScheduler;

import java.util.List;

@Service
public class SchedulerService {

    private TaskScheduler taskScheduler;
    
    @Autowired
    public SchedulerService(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public void addJob(MyJob job, MyTrigger trigger) {
        taskScheduler.addJob(job, trigger);
    }

    public void deleteJob(MyJob job) {
        taskScheduler.deleteJobDetail(job);
    }

    public void updateJob(MyJob job, MyTrigger oldTrigger, MyTrigger newTrigger) {
        taskScheduler.updateJobDetail(job, oldTrigger, newTrigger);
    }

    public List<MyJob> getAllJob() {
        return taskScheduler.getAll();
    }
}