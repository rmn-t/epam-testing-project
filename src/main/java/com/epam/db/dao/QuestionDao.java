package com.epam.db.dao;

import com.epam.exceptions.DBException;
import com.epam.db.model.Answer;
import com.epam.db.model.Question;

import java.sql.Connection;
import java.util.List;

public interface QuestionDao {

    int insertQuestionByTestId(String text, int testId) throws DBException;

    void insertQuestionAndItsAnswersByTestId(String text, int testId, List<Answer> answers) throws DBException;

    Question getQuestionById(int questionId) throws DBException;

    List<Question> getQuestionsByTestId(int testId) throws DBException;

    List<Question> getQuestionsAndAnswersByTestId(int testId) throws DBException;

    void updateQuestionAndItsAnswers(int id, String questionText, List<Answer> answers) throws DBException;

    void updateQuestionTextById(Connection con, String questionText, int questionId) throws DBException;

    void deleteQuestionById(int id, int testId, int questionsLeft) throws DBException;

}
