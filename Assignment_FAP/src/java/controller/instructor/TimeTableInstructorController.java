/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.instructor;

import controller.authentication.BaseRequiredAuthenController;
import controller.authorization.BaseRoleBACController;
import dal.SessionDBContext;
import dal.SlotDBContext;
import entity.Account;
import entity.Feature;
import entity.Session;
import entity.Slot;
import helper.date.DateTimeHelper;
import helper.date.Week;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;

/**
 *
 * @author Admin
 */
public class TimeTableInstructorController extends BaseRoleBACController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, Account account)
            throws ServletException, IOException {
        String raw_year = request.getParameter("year");
        String raw_startDay = request.getParameter("startDay");

        Integer year = null;
        ArrayList<Week> weeks = new ArrayList<>();
        LocalDate startDay = null;
        ArrayList<Date> daysOfWeek = new ArrayList<>();
        SessionDBContext sessDB = new SessionDBContext();
        ArrayList<Session> sessionList = new ArrayList<>();

        if (raw_year == null && raw_startDay == null) {
            year = LocalDate.now().getYear();
            weeks = DateTimeHelper.getWeeks(year);
            startDay = DateTimeHelper.getStartDayWeek(LocalDate.now());
            daysOfWeek = DateTimeHelper.getAllDayOfWeek(startDay);
            Date from = Date.valueOf(startDay);
            Date to = Date.valueOf(startDay.plusDays(6));
            sessionList = sessDB.getTimeTableOfInstructor(account.getInstructor().getId(), from, to);
        }

        if (raw_year != null && raw_startDay == null) {
            year = Integer.valueOf(raw_year);
            weeks = DateTimeHelper.getWeeks(year);
            if (year != LocalDate.now().getYear()) {
                startDay = DateTimeHelper.getStartDayWeek(weeks.get(0).getStartDay());
                daysOfWeek = DateTimeHelper.getAllDayOfWeek(startDay);
                Date from = Date.valueOf(startDay);
                Date to = Date.valueOf(startDay.plusDays(6));
                sessionList = sessDB.getTimeTableOfInstructor(account.getInstructor().getId(), from, to);
            } else {
                startDay = DateTimeHelper.getStartDayWeek(LocalDate.now());
                daysOfWeek = DateTimeHelper.getAllDayOfWeek(startDay);
                Date from = Date.valueOf(startDay);
                Date to = Date.valueOf(startDay.plusDays(6));
                sessionList = sessDB.getTimeTableOfInstructor(account.getInstructor().getId(), from, to);
            }
        }

        if (raw_year == null && raw_startDay != null) {
            startDay = LocalDate.parse(raw_startDay);
            year = startDay.getYear();
            weeks = DateTimeHelper.getWeeks(year);
            daysOfWeek = DateTimeHelper.getAllDayOfWeek(startDay);
            Date from = Date.valueOf(startDay);
            Date to = Date.valueOf(startDay.plusDays(6));
            sessionList = sessDB.getTimeTableOfInstructor(account.getInstructor().getId(), from, to);
        }

        SlotDBContext slotDB = new SlotDBContext();
        ArrayList<Slot> slots = slotDB.getSlot();

        request.setAttribute("year", year);
        request.setAttribute("startDay", startDay);
        request.setAttribute("weeks", weeks);
        request.setAttribute("daysOfWeek", daysOfWeek);
        request.setAttribute("sessionList", sessionList);
        request.setAttribute("slots", slots);
        request.getRequestDispatcher("../view/instructor/timetable.jsp").forward(request, response);
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response,
            Account account, ArrayList<Feature> featureList)
            throws ServletException, IOException {
        processRequest(request, response, account);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response,
            Account account, ArrayList<Feature> featureList)
            throws ServletException, IOException {
        processRequest(request, response, account);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
