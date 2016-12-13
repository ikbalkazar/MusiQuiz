package com.example.restapi.Database;

import com.example.restapi.Model.Question;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.lang.annotation.*;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * Created by ikbalkazar on 08/12/16.
 */
public interface Questions {
    String ID = "id";
    String CHALLENGE_ID = "challengeid";
    String CHOICES = "choices";
    String SONG_URL = "songurl";

    @SqlUpdate("INSERT INTO questions (" + ID + "," + CHALLENGE_ID + "," + SONG_URL + "," + CHOICES +
            ") VALUES (:" + ID + ",:" + CHALLENGE_ID + ",:" + SONG_URL + ",:" + CHOICES + ")")
    void addQuestion(@BindQuestion Question question);

    @Mapper(QuestionMapper.class)
    @SqlQuery("SELECT * FROM questions WHERE " + CHALLENGE_ID + " = :challengeid")
    List<Question> getForChallenge(@Bind("challengeid") String challengeId);

    class QuestionMapper implements ResultSetMapper<Question> {
        @Override
        public Question map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            Array array = resultSet.getArray(CHOICES);
            String[] choices = (String[])(array.getArray());
            return new Question(resultSet.getString(ID), resultSet.getString(CHALLENGE_ID), choices, resultSet.getString(SONG_URL));
        }
    }

    @BindingAnnotation(BindQuestion.QuestionBinderFactory.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER})
    @interface BindQuestion {
        class QuestionBinderFactory implements BinderFactory {
            public Binder build(Annotation annotation) {
                return new Binder<BindQuestion, Question>() {
                    public void bind(SQLStatement q, BindQuestion bind, Question arg) {
                        Array choicesArray = null;
                        try {
                            choicesArray = q.getContext().getConnection().createArrayOf("text", arg.getChoices());
                        } catch (Exception e) {
                            System.err.println("ERROR - Could not create sql array for choices...");
                        }
                        q.bind(ID, arg.getId());
                        q.bind(CHALLENGE_ID, arg.getChallengeId());
                        q.bindBySqlType(CHOICES, choicesArray, Types.ARRAY);
                        q.bind(SONG_URL, arg.getSongUrl());
                    }
                };
            }
        }
    }
}
