package Model;

import java.io.Serializable;

public class Student implements Serializable {
    private int studentID;
    private String studentName;
    private String studentMobileNo;


    public Student(){

    }


    public Student(int studentID, String studentName, String studentMobileNo) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.studentMobileNo = studentMobileNo;
    }


    public int getStudentID() {
        return studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentMobileNo() {
        return studentMobileNo;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentID=" + studentID +
                ", studentName='" + studentName + '\'' +
                "\n, studentMobileNo='" + studentMobileNo + '\'' +
                '}';
    }
}
