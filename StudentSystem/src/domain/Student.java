package domain;

/**
 * <student idcard="111" examid="222">
 * <name>张三</name>
 * <location>沈阳</location>
 * <grade>89</grade>
 * </student>
 */
public class Student {

    private String idcard;
    private String examid;
    private String name;
    private String location;
    private int grade;

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getExamid() {
        return examid;
    }

    public void setExamid(String examid) {
        this.examid = examid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "idcard='" + idcard + '\'' +
                ", examid='" + examid + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", grade=" + grade +
                '}';
    }
}
