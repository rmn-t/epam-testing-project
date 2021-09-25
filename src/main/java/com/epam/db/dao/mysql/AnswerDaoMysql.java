package com.epam.db.dao.mysql;

import com.epam.db.accessors.DatabaseAccessable;
import com.epam.db.dao.AnswerDao;
import com.epam.db.model.Answer;
import com.epam.exceptions.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL implementation of AnswerDao interface
 */
public class AnswerDaoMysql implements AnswerDao {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AnswerDaoMysql.class);
    private final DatabaseAccessable databaseAccessable;

    /**
     * Constructor allows to pick which DB connection will be used for internal method execution, injected via interface
     *
     * @param databaseAccessable database utility instance that will be used for DAO operations
     */
    public AnswerDaoMysql(DatabaseAccessable databaseAccessable) {
        this.databaseAccessable = databaseAccessable;
    }

    public List<Answer> getAnswersByQuestionId(int questionId) throws DBException {
        List<Answer> results = new ArrayList<>();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = databaseAccessable.getConnection();
            prepStmt = con.prepareStatement("SELECT id,question_id,text,isCorrect FROM answer WHERE question_id = ?;");
            prepStmt.setInt(1, questionId);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                results.add(new Answer.Builder().setId(rs.getInt("id"))
                        .setText(rs.getString("text"))
                        .setQuestionId(rs.getInt("question_id"))
                        .setCorrect(rs.getBoolean("isCorrect"))
                        .build()
                );
            }
            logger.debug("Successfully obtained answers by question_id {}.", questionId);
        } catch (SQLException e) {
            logger.error("Collecting answers by question id failed.", e);
            throw new DBException("Collecting answers by question id failed.", e);
        } finally {
            databaseAccessable.closeAllInOrder(rs, prepStmt, con);
        }
        return results;
    }

    public void updateAnswersByQuestionId(Connection con, int questionId, List<Answer> answers) throws DBException {
        try {
            deleteAnswersByQuestionId(con, questionId);
            for (Answer a : answers) {
                insertAnswerByQuestionId(con, questionId, a.getText(), a.getIsCorrect());
            }
            logger.info("${} answers were successfully updated for {} question_id.", answers.size(), questionId);
        } catch (DBException e) {
            logger.error("Updating answers by question id failed.", e);
            throw new DBException("Updating answers by question id failed.", e);
        }


    }

    public void insertAnswersByQuestionId(int questionId, List<Answer> answers) throws DBException {
        Connection con = null;
        try {
            con = databaseAccessable.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            for (Answer a : answers) {
                insertAnswerByQuestionId(con, questionId, a.getText(), a.getIsCorrect());
            }
            con.commit();
            logger.info("Inserted {} answers for question_id {}.", answers.size(), questionId);
        } catch (SQLException | DBException e) {
            logger.error("Failed inserting answers for question_id {}.", questionId, e);
            throw new DBException("Failed inserting answers.", e);
        } finally {
            databaseAccessable.close(con);
        }
    }

    public void insertAnswerByQuestionId(Connection con, int questionId, String answerText, boolean answerIsCorrect) throws DBException {
        PreparedStatement prepStmt = null;
        try {
            prepStmt = con.prepareStatement("INSERT INTO answer(question_id,text,isCorrect) values(?,?,?);");
            int k = 1;
            prepStmt.setInt(k++, questionId);
            prepStmt.setString(k++, answerText);
            prepStmt.setBoolean(k++, answerIsCorrect);
            prepStmt.executeUpdate();
            logger.info("Inserted new record to answer table. question_id : {} | text : {} | isCorrect : {}", questionId, answerText, answerIsCorrect);
        } catch (SQLException e) {
            logger.error("Inserting new answer failed.", e);
            throw new DBException("Inserting new answer failed.", e);
        } finally {
            databaseAccessable.close(prepStmt);
        }
    }

    public void deleteAnswersByQuestionId(Connection con, int questionId) throws DBException {
        PreparedStatement prepStmt = null;
        try {
            prepStmt = con.prepareStatement("DELETE FROM answer WHERE question_id = ?;");
            prepStmt.setInt(1, questionId);
            prepStmt.executeUpdate();
            logger.info("All questions for test_id {} were deleted.", questionId);
        } catch (SQLException e) {
            logger.error("Answers for question_id {} couldn't be deleted", questionId, e);
            throw new DBException("Answers for question_id {} couldn't be deleted", e);
        } finally {
            databaseAccessable.closeAllInOrder(prepStmt);
        }
    }

}
