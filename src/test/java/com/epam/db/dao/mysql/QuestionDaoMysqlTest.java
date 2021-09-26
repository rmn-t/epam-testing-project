package com.epam.db.dao.mysql;

import com.epam.db.accessors.DatabaseAccessable;
import com.epam.db.dao.AnswerDao;
import com.epam.db.dao.QuestionDao;
import com.epam.db.model.Answer;
import com.epam.db.model.Question;
import com.epam.exceptions.DBException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoMysqlTest {
    static DatabaseAccessable dbAccessor = DBSetup.DB_UTIL;
    QuestionDao questionDao = DBSetup.QUESTION_DAO_TEST;
    AnswerDao answerDao = DBSetup.ANSWER_DAO_TEST;

    @Before
    public void beforeTest() throws SQLException, DBException {
        Connection con = dbAccessor.getConnection();
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.addBatch(DBSetup.DEACTIVATE_FOREIGN_KEYS);
        DBSetup.addStatementsToBatch(stmt, DBSetup.COMPLEXITY_RELATED);
        DBSetup.addStatementsToBatch(stmt, DBSetup.SUBJECT_RELATED);
        DBSetup.addStatementsToBatch(stmt, DBSetup.TEST_RELATED);
        DBSetup.addStatementsToBatch(stmt, DBSetup.QUESTION_RELATED);
        DBSetup.addStatementsToBatch(stmt, DBSetup.ANSWER_RELATED);
        stmt.executeBatch();
        dbAccessor.close(con);
    }

    @Test
    public void Should_return_question_by_id() throws DBException {
        Question question = questionDao.getQuestionById(25);
        System.out.println(question);
        Assert.assertEquals(25,question.getId());
        Assert.assertEquals("Question, id #25 for test: Test # 5",question.getText());
    }

    @Test
    public void Should_correctly_insert_question_by_testId() throws DBException {
        int questionId = questionDao.insertQuestionByTestId("abc",1);
        Question question = questionDao.getQuestionById(questionId);
        Assert.assertEquals(questionId,question.getId());
        Assert.assertEquals(1,question.getTestId());
        Assert.assertEquals("abc",question.getText());
    }

    @Test
    public void Should_correctly_delete_question_by_id() throws DBException {
        questionDao.deleteQuestionById(5,5,5);
        Question question = questionDao.getQuestionById(5);
        Assert.assertEquals(0,question.getId());
    }

    @Test
    public void Should_correctly_return_questions_and_answers_by_test_id() throws DBException {
        List<Question> actualAnswers = questionDao.getQuestionsAndAnswersByTestId(1);
        List<Question> expected = new ArrayList<>();
        expected.add(new Question.Builder().setId(1).build());
        expected.add(new Question.Builder().setId(2).build());
        expected.add(new Question.Builder().setId(3).build());
        List<Answer> expectedAnswers1 = new ArrayList<>();
        expectedAnswers1.add(new Answer.Builder().setId(1).build());
        expectedAnswers1.add(new Answer.Builder().setId(40).build());
        expectedAnswers1.add(new Answer.Builder().setId(61).build());
        List<Answer> expectedAnswers2 = new ArrayList<>();
        expectedAnswers2.add(new Answer.Builder().setId(2).build());
        List<Answer> expectedAnswers3 = new ArrayList<>();
        expectedAnswers3.add(new Answer.Builder().setId(3).build());
        expectedAnswers3.add(new Answer.Builder().setId(49).build());
        Assert.assertEquals(expected,actualAnswers);
        Assert.assertEquals(expectedAnswers1,actualAnswers.get(0).getAnswers());
        Assert.assertEquals(expectedAnswers2,actualAnswers.get(1).getAnswers());
        Assert.assertEquals(expectedAnswers3,actualAnswers.get(2).getAnswers());
    }

    @Test
    public void Should_correctly_update_question_and_its_answers() throws DBException {
        List<Answer> newAnswers = new ArrayList<>();
        newAnswers.add(new Answer.Builder().setQuestionId(1).setCorrect(false).setText("a").build());
        newAnswers.add(new Answer.Builder().setQuestionId(1).setCorrect(true).setText("b").build());
        newAnswers.add(new Answer.Builder().setQuestionId(1).setCorrect(true).setText("c").build());
        questionDao.updateQuestionAndItsAnswers(1,"abc",newAnswers);
        List<Answer> expectedAnswers = new ArrayList<>();
        expectedAnswers.add(new Answer.Builder().setId(76).build());
        expectedAnswers.add(new Answer.Builder().setId(77).build());
        expectedAnswers.add(new Answer.Builder().setId(78).build());
        Question question = questionDao.getQuestionById(1);
        List<Answer> actualAnswers = answerDao.getAnswersByQuestionId(1);
        Assert.assertEquals(1,question.getId());
        Assert.assertEquals(expectedAnswers,actualAnswers);
    }
    
    @Test
    public void Should_correctly_insert_question_and_its_answers_by_test_id() throws DBException {
        List<Answer> newAnswers = new ArrayList<>();
        newAnswers.add(new Answer.Builder().setQuestionId(1).setCorrect(false).setText("a").build());
        newAnswers.add(new Answer.Builder().setQuestionId(1).setCorrect(true).setText("b").build());
        newAnswers.add(new Answer.Builder().setQuestionId(1).setCorrect(true).setText("c").build());
        questionDao.insertQuestionAndItsAnswersByTestId("abc",1,newAnswers);
        Question actualQuestion = questionDao.getQuestionById(26);
        List<Answer> actualAnswers = answerDao.getAnswersByQuestionId(26);
        List<Answer> expectedAnswers = new ArrayList<>();
        expectedAnswers.add(new Answer.Builder().setId(76).build());
        expectedAnswers.add(new Answer.Builder().setId(77).build());
        expectedAnswers.add(new Answer.Builder().setId(78).build());
        Assert.assertEquals(26,actualQuestion.getId());
        Assert.assertEquals(expectedAnswers,actualAnswers);
    }
    
    @Test
    public void Should_correctly_update_question_text_by_question_id() throws SQLException, DBException {
        questionDao.updateQuestionTextById(dbAccessor.getConnection(),"123",3);
        questionDao.updateQuestionTextById(dbAccessor.getConnection(),"abc",3);
        Question actualQuestion = questionDao.getQuestionById(3);
        Assert.assertEquals(3,actualQuestion.getId());
        Assert.assertEquals("abc",actualQuestion.getText());
    }

    @After
    public void afterTest() throws SQLException, DBException {
        Connection con = dbAccessor.getConnection();
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.addBatch(DBSetup.ACTIVATE_FOREIGN_KEYS);
        stmt.executeBatch();
        dbAccessor.close(con);
    }

}