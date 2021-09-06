package com.epam.db.dao;

import com.epam.exceptions.DBException;
import com.epam.db.model.Answer;

import java.sql.Connection;
import java.util.List;

public interface AnswerDao {
    List<Answer> getAnswersByQuestionId(int questionId) throws DBException;

    void updateAnswersByQuestionId(Connection con, int questionId, List<Answer> answers) throws DBException;

    void insertAnswersByQuestionId(int questionId, List<Answer> answers) throws DBException;

    void insertAnswerByQuestionId(Connection con, int questionId, String answerText, boolean answerIsCorrect) throws DBException;

    void deleteAnswersByQuestionId(Connection con, int questionId) throws DBException;





}
