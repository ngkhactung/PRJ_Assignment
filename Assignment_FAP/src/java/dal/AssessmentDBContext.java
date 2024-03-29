/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Assessment;
import helper.calculating.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class AssessmentDBContext extends DBContext {

    //Get all assessments except final exam, final reset exam and practical exam 
    //of a course belonging to the group given id
    public ArrayList<Assessment> getAssessmentByGroup(int groupID) {
        ArrayList<Assessment> assessList = new ArrayList<>();
        try {
            String sql = "select g.ID, a.ID as AssessmentID, a.Type, a.Name, a.Weight\n"
                    + "from Groups g inner join Course c on g.CourseID = c.ID\n"
                    + "		 inner join Assessment a on c.ID = a.CourseID\n"
                    + "where g.ID = ? and a.Type <> 'Final Exam' \n"
                    + "and a.Type <> 'Final Exam Resit' and a.Type <> 'Practical Exam'";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, groupID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Assessment assessment = new Assessment();

                assessment.setId(rs.getInt("AssessmentID"));
                assessment.setType(rs.getString("Type"));
                assessment.setName(rs.getString("Name"));
                assessment.setWeight(rs.getFloat("Weight"));

                assessList.add(assessment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssessmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return assessList;
    }

    //Get all assessments of a course by given a course id
    public ArrayList<Assessment> getAssessmentByCourse(int courseID) {
        ArrayList<Assessment> assessList = new ArrayList<>();
        try {
            String sql = "select g.CourseID, a.ID as AssessmentID, a.Type, a.Name, a.Weight\n"
                    + "from Groups g inner join Course c on g.CourseID = c.ID\n"
                    + "              inner join Assessment a on c.ID = a.CourseID\n"
                    + "where g.CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Assessment assessment = new Assessment();

                assessment.setId(rs.getInt("AssessmentID"));
                assessment.setType(rs.getString("Type"));
                assessment.setName(rs.getString("Name"));
                assessment.setWeight(rs.getFloat("Weight"));

                assessList.add(assessment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssessmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return assessList;
    }

    //Get all types of assessment and total weight of each type for a course by course id
    public ArrayList<Type> getTypesOfCourse(int courseID) {
        ArrayList<Type> typeList = new ArrayList<>();
        try {
            String sql = "select a.Type, SUM(a.Weight) as Total\n"
                    + "from Assessment a\n"
                    + "where a.CourseID = ?\n"
                    + "Group By a.Type\n"
                    + "Order by \n"
                    + "    case \n"
                    + "        when a.Type = 'Practical Exam' then 1\n"
                    + "        when a.Type = 'Final Exam' then 2\n"
                    + "        when a.Type = 'Final Exam Resit' then 3\n"
                    + "        else 0\n"
                    + "    end asc";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Type type = new Type();

                type.setName(rs.getString("Type"));
                type.setWeight(rs.getFloat("Total"));

                typeList.add(type);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssessmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return typeList;
    }
}
