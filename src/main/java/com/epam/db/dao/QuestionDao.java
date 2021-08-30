package com.epam.db.dao;

import com.epam.db.DBUtil;
import com.epam.db.entities.Answer;
import com.epam.db.entities.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDao {

    public static int insertQuestion(String text,int testId) {
        int res = -1;
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet generatedKeys = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("INSERT INTO question(text,test_id) VALUES(?,?);", Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            prepStmt.setString(k++,text);
            prepStmt.setInt(k++,testId);
            int affectedRows = prepStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            generatedKeys = prepStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                res = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(prepStmt,con);
        }
        return res;
    }

    public static Question getQuestionById(int questionId) {
        Question q = new Question();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("SELECT id,text,test_id FROM question WHERE id = ?;");
            prepStmt.setInt(1,questionId);
            rs = prepStmt.executeQuery();
            rs.next();
            q = mapQuestion(rs);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(rs,prepStmt,con);
        }
        return q;
    }

    public static List<Question> getQuestionsByTestId(int testId) {
        List<Question> results = new ArrayList<>();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("SELECT id,text,test_id FROM question WHERE test_id = ?;");
            prepStmt.setInt(1,testId);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                results.add(mapQuestion(rs));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(rs,prepStmt,con);
        }
        return results;
    }

    public static List<Question> getQuestionsAndAnswersByTestId(int testId) {
        List<Question> results = new ArrayList<>();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("SELECT id,text,test_id FROM question WHERE test_id = ?;");
            prepStmt.setInt(1,testId);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                results.add(mapQuestionAndAnswers(rs));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(rs,prepStmt,con);
        }
        return results;
    }

    public static Question mapQuestion(ResultSet rs) {
        Question q = new Question();
        try {
            q.setId(rs.getInt("id"));
            q.setText(rs.getString("text"));
            q.setTestId(rs.getInt("test_id"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return q;
    }

    public static Question mapQuestionAndAnswers(ResultSet rs) {
        Question q = mapQuestion(rs);
        q.setAnswers(AnswerDao.getAnswersByQuestionId(q.getId()));
        return q;
    }

    public static void updateQuestionAndItsAnswers(int id, String questionText, List<Answer> answers) {
        Connection con = null;
        try {
            con = DBUtil.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            System.out.println("con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);()");
            updateQuestionTextById(con,questionText,id);
            System.out.println("updateQuestionTextById(con,questionText,id);()");
            AnswerDao.updateAnswersByQuestionId(con,id,answers);
            System.out.println("AnswerDao.updateAnswersByQuestionId(con,id,answers);()");
            con.commit();
        } catch (SQLException exception) {
            try {
                if (con != null) {
                    con.rollback();
                    System.out.println("rolling back yikes");
                }
            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
        } finally {
            DBUtil.closeAllInOrder(con);
        }
    }

    private static void updateQuestionTextById(Connection con, String questionText, int questionId) {
        PreparedStatement prepStmt = null;
        try {
            prepStmt = con.prepareStatement("UPDATE question SET text = ? WHERE id = ?;");
            int k = 1;
            prepStmt.setString(k++,questionText);
            prepStmt.setInt(k++,questionId);
            prepStmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(prepStmt);
        }
    }

    public static void deleteQuestionById(int id) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("DELETE FROM question WHERE id = ?;");
            prepStmt.setInt(1,id);
            prepStmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(prepStmt,con);
        }
    }

}
