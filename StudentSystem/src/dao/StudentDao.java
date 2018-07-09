package dao;

import domain.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import utils.XmlUtils;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * <student idcard="111" examid="222">
 * <name>张三</name>
 * <location>沈阳</location>
 * <grade>89</grade>
 * </student>
 */
public class StudentDao {

    public void add(Student student) {
        try {
            Document document = XmlUtils.getDocument();

            Element student_element = document.createElement("student");
            student_element.setAttribute("idcard", student.getIdcard());
            student_element.setAttribute("examid", student.getExamid());

            Element name_element = document.createElement("name");
            name_element.setTextContent(student.getName());

            Element location_element = document.createElement("location");
            location_element.setTextContent(student.getLocation());

            Element grade_element = document.createElement("grade");
            grade_element.setTextContent(String.valueOf(student.getGrade()));

            student_element.appendChild(name_element);
            student_element.appendChild(location_element);
            student_element.appendChild(grade_element);

            document.getElementsByTagName("exam").item(0).appendChild(student_element);

            XmlUtils.write2Xml(document);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    public Student find(String examid) {
        try {
            Document document = XmlUtils.getDocument();
            NodeList list = document.getElementsByTagName("student");

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element tmp = (Element) node;
                    if (tmp.getAttribute("examid").equals(examid)) {
                        return getStudent(tmp);
                    }
                }
            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void delete(String name) {
        try {
            Document document = XmlUtils.getDocument();
            NodeList list = document.getElementsByTagName("student");

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element tmp = (Element) node;
                    if (tmp.getElementsByTagName("name").item(0).getTextContent().equals(name)) {
                        tmp.getParentNode().removeChild(tmp);
                        XmlUtils.write2Xml(document);
                        System.out.println("操作成功");
                        return;
                    }
                }
            }
            System.out.println("删除学生失败,学生不存在");
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private Student getStudent(Element which) {

        if (which == null) {
            return null;
        }

        Student student = new Student();

        String idcard = which.getAttribute("idcard");
        String examid = which.getAttribute("examid");
        String name = which.getElementsByTagName("name").item(0).getTextContent();
        String location = which.getElementsByTagName("location").item(0).getTextContent();
        String grade = which.getElementsByTagName("grade").item(0).getTextContent();

        student.setName(name);
        student.setLocation(location);
        student.setIdcard(idcard);
        if (grade != null) {
            student.setGrade(Integer.parseInt(grade));
        }
        student.setExamid(examid);

        return student;
    }
}
