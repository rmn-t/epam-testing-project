package com.epam.db.dao;

import com.epam.db.DBUtil;
import com.epam.db.entities.Answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerDao {
    public AnswerDao() {
    }

    public static List<Answer> getAnswersByQuestionId(int questionId) {
        List<Answer> results = new ArrayList<>();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("SELECT id,question_id,text,isCorrect FROM answer WHERE question_id = ?;");
            prepStmt.setInt(1,questionId);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                results.add(mapAnswer(rs));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(rs,prepStmt,con);
        }
        return results;
    }

    public static void updateAnswersByQuestionId(Connection con,int questionId,List<Answer> answers) {
        deleteOldAnswers(con,questionId);
        for (Answer a : answers) {
            insertAnswerByQuestionId(con,questionId,a.getText(),a.getIsCorrect());
        }
    }

    public static void insertAnswersByQuestionId(int questionId,List<Answer> answers) {
        Connection con = null;
        try {
            con = DBUtil.getConnection();
            for (Answer a : answers) {
                insertAnswerByQuestionId(con,questionId,a.getText(),a.getIsCorrect());
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtil.close(con);
        }
    }

    public static void insertAnswerByQuestionId(Connection con,int questionId,String answerText,boolean answerIsCorrect) {
        PreparedStatement prepStmt = null;
        try {
            prepStmt = con.prepareStatement("INSERT INTO answer(question_id,text,isCorrect) values(?,?,?);");
            int k = 1;
            prepStmt.setInt(k++,questionId);
            prepStmt.setString(k++,answerText);
            prepStmt.setBoolean(k++,answerIsCorrect);
            prepStmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtil.close(prepStmt);
        }
    }

    private static void deleteOldAnswers(Connection con,int questionId) {
        PreparedStatement prepStmt = null;
        try {
            prepStmt = con.prepareStatement("DELETE FROM answer WHERE question_id = ?;");
            prepStmt.setInt(1,questionId);
            prepStmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(prepStmt);
        }
    }

    private static Answer mapAnswer(ResultSet rs) {
        Answer answer = new Answer();
        try {
            answer.setId(rs.getInt("id"));
            answer.setText(rs.getString("text"));
            answer.setQuestionId(rs.getInt("question_id"));
            answer.setIsCorrect(rs.getBoolean("isCorrect"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return answer;
    }
}
