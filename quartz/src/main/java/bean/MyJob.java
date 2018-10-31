package bean;

import java.util.List;

public class MyJob {

    private String jobName;
    private String jobGroup;
    private String description;
    private String jobClassName;
    private List<MyTrigger> triggers;

    public MyJob(String jobName, String jobGroup, String description, String jobClassName) {
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.description = description;
        this.jobClassName = jobClassName;
    }

    public List<MyTrigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<MyTrigger> triggers) {
        this.triggers = triggers;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }
}