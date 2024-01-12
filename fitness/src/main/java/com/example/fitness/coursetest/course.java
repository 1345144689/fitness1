package com.example.fitness.coursetest;

/**
 * 课程类
 */
import com.alibaba.excel.annotation.ExcelProperty;
public class course {
    @ExcelProperty("课程编号")
    private String coursecno;

    @ExcelProperty("课程名字")
    private String coursename;

    @ExcelProperty("代课教练编号")
    private String coachcno;

    @ExcelProperty("课程描述")
    private String coursedescribe;

    public course(String coursecno, String coursename, String coachcno, String coursedescribe) {
        this.coursecno = coursecno;
        this.coursename = coursename;
        this.coachcno = coachcno;
        this.coursedescribe = coursedescribe;
    }

    public String getCoursecno() {
        return coursecno;
    }

    public void setCoursecno(String coursecno) {
        this.coursecno = coursecno;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getCoachcno() {
        return coachcno;
    }

    public void setCoachcno(String coachcno) {
        this.coachcno = coachcno;
    }

    public String getCoursedescribe() {
        return coursedescribe;
    }

    public void setCoursedescribe(String coursedescribe) {
        this.coursedescribe = coursedescribe;
    }
}