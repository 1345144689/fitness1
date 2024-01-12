package com.example.fitness.caochtest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
/**
 * 教练类
 */
public class coach {
    String coachname;
    String coachsex;
    String coachtel;
    String coachcno;
    String coursename;

    public coach(String coachname, String coachsex, String coachtel, String coachcno, String coursename) {
        this.coachname = coachname;
        this.coachsex = coachsex;
        this.coachtel = coachtel;
        this.coachcno = coachcno;
        this.coursename = coursename;
    }

    public String getCoachname() {
        return coachname;
    }

    public void setCoachname(String coachname) {
        this.coachname = coachname;
    }

    public String getCoachsex() {
        return coachsex;
    }

    public void setCoachsex(String coachsex) {
        this.coachsex = coachsex;
    }

    public String getCoachtel() {
        return coachtel;
    }

    public void setCoachtel(String coachtel) {
        this.coachtel = coachtel;
    }

    public String getCoachcno() {
        return coachcno;
    }

    public void setCoachcno(String coachcno) {
        this.coachcno = coachcno;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }
}