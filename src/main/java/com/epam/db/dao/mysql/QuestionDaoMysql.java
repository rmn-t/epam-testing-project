package com.epam.db.dao.mysql;

import com.epam.db.accessors.DatabaseAccessable;
import com.epam.db.dao.AnswerDao;
import com.epam.db.dao.QuestionDao;
import com.epam.db.dao.TestDao;
import com.epam.db.model.Answer;
import com.epam.db.model.Question;
import com.epam.exceptions.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL implementation of QuestionDao interface
 */
public class QuestionDaoMysql implements QuestionDao {
    private final Logger logger = LoggerFactory.getLogger(QuestionDaoMysql.class);
    private final DatabaseAccessable databaseAccessable;
    private final TestDao testDao;
    private final AnswerDao answerDao;

    /**
     * Constructor allows to pick which DB connection will be used for internal method execution, injected via interface
     *
     * @param databaseAccessable database utility instance that will be used for DAO operations
     */
    public QuestionDaoMysql(DatabaseAccessable databaseAccessable) {
        this.databaseAccessable = databaseAccessable;
        this.testDao = new TestDaoMysql(databaseAccessable);
        this.answerDao = new AnswerDaoMysql(databaseAccessable);
    }

    /**
     * Constructor allows pass in already initiated DAOs that will be used internally
     * @param databaseAccessable database utility instance that will be used for DAO operations
     * @param testDao instance of implemented test dao
     * @param answerDao instance of implemented answer dao
     */
    public QuestionDaoMysql(DatabaseAccessable databaseAccessable,TestDao testDao, AnswerDao answerDao) {
        this.databaseAccessable = databaseAccessable;
        this.testDao = testDao;
        this.answerDao = answerDao;
    }

    public int insertQuestionByTestId(String text, int testId) throws DBException {
        int res = -1;
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet generatedKeys = null;
        try {
            con = databaseAccessable.getConnection();
            prepStmt = con.prepareStatement("INSERT INTO question(text,test_id) VALUES(?,?);", Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            prepStmt.setString(k++, text);
            prepStmt.setInt(k++, testId);
            prepStmt.executeUpdate();
            generatedKeys = prepStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                res = generatedKeys.getInt(1);
            } else {
                throw new DBException("Creating question failed, no ID obtained.", new Throwable());
            }
            logger.debug("Successfully added new question to test_id {}.", testId);
        } catch (SQLException e) {
            logger.error("Couldn't add question with text : {} , for test_id {}.", text, testId);
            throw new DBException("Couldn't add question", e);
        } finally {
            databaseAccessable.closeAllInOrder(generatedKeys, prepStmt, con);
        }
        return res;
    }

    @Override
    public void insertQuestionAndItsAnswersByTestId(String text, int testId, List<Answer> answers) throws DBException {
        Connection con = null;
        try {
            con = databaseAccessable.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            int questionId = insertQuestionByTestId(text, testId);
            answerDao.insertAnswersByQuestionId(questionId, answers);
            con.commit();
            logger.debug("Successfully inserted question_id {} and it's answers.", questionId);
        } catch (SQLException | DBException e) {
            try {
                if (con != null) {
                    con.rollback();
                    logger.error("Failed inserting question and it's answers", e);
                }
            } catch (SQLException err) {
                logger.error("Failed to execute rollback.", err);
            }
            throw new DBException("Failed update for question_id", e);
        } finally {
            databaseAccessable.closeAllInOrder(con);
        }
    }

    public Question getQuestionById(int questionId) throws DBException {
        Question q = new Question();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = databaseAccessable.getConnection();
            prepStmt = con.prepareStatement("SELECT id,text,test_id FROM question WHERE id = ?;");
            prepStmt.setInt(1, questionId);
            rs = prepStmt.executeQuery();
            if (rs.next()) {
                q = new Question.Builder().setId(rs.getInt("id")).setTestId(rs.getInt("test_id")).setText(rs.getString("text")).build();
            }
        } catch (SQLException e) {
            logger.error("Failed to get question by id {}.", questionId, e);
            throw new DBException("Failed to get question by id", e);
        } finally {
            databaseAccessable.closeAllInOrder(rs, prepStmt, con);
        }
        return q;
    }

    public List<Question> getQuestionsAndAnswersByTestId(int testId) throws DBException {
        List<Question> results = new ArrayList<>();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = databaseAccessable.getConnection();
            prepStmt = con.prepareStatement("SELECT id,text,test_id FROM question WHERE test_id = ?;");
            prepStmt.setInt(1, testId);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                Question q = new Question.Builder()
                        .setId(rs.getInt("id"))
                        .setTestId(rs.getInt("test_id"))
                        .setText(rs.getString("text"))
                        .build();
                q.setAnswers(answerDao.getAnswersByQuestionId(q.getId()));
                results.add(q);
            }
            logger.debug("Successfully obtained {} questions for test_id {}.", results.size(), testId);
        } catch (DBException | SQLException e) {
            logger.error("Couldn't obtain questions and answers by test_id {}.", testId, e);
            throw new DBException("Couldn't obtain questions and answers by test_id", e);
        } finally {
            databaseAccessable.closeAllInOrder(rs, prepStmt, con);
        }
        return results;
    }

    public void updateQuestionAndItsAnswers(int id, String questionText, List<Answer> answers) throws DBException {
        Connection con = null;
        try {
            con = databaseAccessable.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            updateQuestionTextById(con, questionText, id);
            new AnswerDaoMysql(databaseAccessable).updateAnswersByQuestionId(con, id, answers);
            con.commit();
            logger.debug("Successfully updated question_id {} and it's answers.", id);
        } catch (SQLException | DBException e) {
            try {
                if (con != null) {
                    con.rollback();
                    logger.error("Failed update for question_id {}.", id, e);
                }
            } catch (SQLException err) {
                logger.error("Failed to execute rollback.", err);
            }
            throw new DBException("Failed update for question_id", e);
        } finally {
            databaseAccessable.closeAllInOrder(con);
        }
    }

    public void updateQuestionTextById(Connection con, String questionText, int questionId) throws DBException {
        PreparedStatement prepStmt = null;
        try {
            prepStmt = con.prepareStatement("UPDATE question SET text = ? WHERE id = ?;");
            int k = 1;
            prepStmt.setString(k++, questionText);
            prepStmt.setInt(k++, questionId);
            prepStmt.executeUpdate();
            logger.debug("Successfully updated question_id {}.", questionId);
        } catch (SQLException e) {
            logger.error("Failed to update question_id {}.", questionId, e);
            throw new DBException("Failed to update question by id.", e);
        } finally {
            databaseAccessable.closeAllInOrder(prepStmt);
        }
    }

    public void deleteQuestionById(int id, int testId, int questionsLeft) throws DBException {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = databaseAccessable.getConnection();
            prepStmt = con.prepareStatement("DELETE FROM question WHERE id = ?;");
            prepStmt.setInt(1, id);
            prepStmt.executeUpdate();
            if (questionsLeft == 1) {
                testDao.deactivateTestById(con, testId);
            }
            logger.debug("Successfully deleted question_id {}", id);
        } catch (SQLException e) {
            logger.error("Failed to delete question by id {}.", id, e);
            throw new DBException("Failed to delete question by id.", e);
        } finally {
            databaseAccessable.closeAllInOrder(prepStmt, con);
        }
    }

}
