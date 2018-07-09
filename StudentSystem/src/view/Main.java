package view;

import dao.StudentDao;
import domain.Student;

import java.util.Scanner;

/**
 * <exam>
 * <student idcard="111" examid="222">
 * <name>张三</name>
 * <location>沈阳</location>
 * <grade>89</grade>
 * </student>
 * </exam>
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("添加学生(a)  查找学生(b) 删除学生(c)");
        System.out.println("输入操作的类型");

        Scanner in = new Scanner(System.in);
        String type = in.nextLine();

        StudentDao dao = new StudentDao();

        if (type.equalsIgnoreCase("a")) {
            System.out.println("请输入学生姓名");
            String name = in.nextLine();

            System.out.println("请输入学生的学号");
            String idcard = in.nextLine();

            System.out.println("请输入学生的考试号");
            String examid = in.nextLine();

            System.out.println("请输入学生的成绩");
            String grade = in.nextLine();

            System.out.println("请输入学生的所在城市");
            String location = in.nextLine();

            Student student = new Student();
            student.setExamid(examid);
            student.setGrade(Integer.parseInt(grade));
            student.setIdcard(idcard);
            student.setLocation(location);
            student.setName(name);

            dao.add(student);

        } else if (type.equalsIgnoreCase("b")) {
            System.out.println("请输入学生的考试号");
            String examid = in.nextLine();

            Student student = dao.find(examid);

            if (student == null) {
                System.out.println("学生未找到");
            } else {
                System.out.println(student.toString());
            }
        } else if (type.equalsIgnoreCase("c")) {
            System.out.println("请输入学生的名字");
            String name = in.nextLine();
            dao.delete(name);
        }

    }

}
