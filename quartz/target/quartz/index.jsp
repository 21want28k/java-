<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<html>
<body>
<h2>Hello World!</h2>
添加:<br>
<form action="/quartz/add" method="post">
    cron:<input type="text" name="cron"><br>
    jobName:<input type="text" name="jobName"><br>
    jobGroup:<input type="text" name="jobGroup"><br>
    jobClassName:<input type="text" name="jobClassName"><br>
    triggerName:<input type="text" name="name"><br>
    triggerGroup:<input type="text" name="group"><br>
    startTime<input type="text" name="startTime"><br>
    endTime<input type="text" name="endTime"><br>
    提交:<input type="submit">
</form>
<br>
删除:
<form action="/quartz/delete" method="post">
    jobName:<input type="text" name="jobName"><br>
    jobGroup:<input type="text" name="jobGroup"><br>
    triggerName:<input type="text" name="name"><br>
    triggerGroup:<input type="text" name="group"><br>
    提交:<input type="submit">
</form>
<br>
更新:<br>
<form action="/quartz/update" method="post">
    cron:<input type="text" name="cron"><br>
    jobName:<input type="text" name="jobName"><br>
    jobGroup:<input type="text" name="jobGroup"><br>
    jobClassName:<input type="text" name="jobClassName"><br>
    oldTriggerName:<input type="text" name="name"><br>
    oldTriggerGroup:<input type="text" name="group"><br>
    oldStartTime<input type="text" name="startTime"><br>
    oldEndTime<input type="text" name="endTime"><br>
    triggerName:<input type="text" name="name"><br>
    triggerGroup:<input type="text" name="group"><br>
    startTime<input type="text" name="startTime"><br>
    endTime<input type="text" name="endTime"><br>
    提交:<input type="submit">
</form>
<SCRIPT>
    function submitUpdate() {

    }
</SCRIPT>
</body>
</html>