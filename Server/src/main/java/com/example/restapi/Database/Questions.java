package com.example.restapi.Database;

import com.example.restapi.Model.Question;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.lang.annotation.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ikbalkazar on 29/11/16.
 */
public interface Questions {
    String ID = "id";
    String CHOICES = "choices";
    String URL = "url";
    String SONG_NAME = "songname";
    String ARTIST_NAME = "artistname";

    @Mapper(QuestionMapper.class)
    @SqlQuery("SELECT * FROM questions WHERE " + ID + " = :id")
    Question getQuestion(@Bind("id") String id);

    @SqlUpdate("INSERT INTO questions (" + ID + "," + CHOICES + "," + URL + "," + SONG_NAME + "," + ARTIST_NAME + ") " +
                "VALUES (:" + ID + ",:" + CHOICES + ",:" + SONG_NAME + ",:" + ARTIST_NAME + ")")
    void addQuestion(@BindBean("question") Question question);

    class QuestionMapper implements ResultSetMapper<Question> {
        @Override
        public Question map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            String[] choicesArray = (String[])(resultSet.getArray(CHOICES).getArray());
            return new Question(resultSet.getString(ID),
                    choicesArray,
                    resultSet.getString(URL),
                    resultSet.getString(SONG_NAME),
                    resultSet.getString(ARTIST_NAME));
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
                        q.bind(ID, arg.getId());
                        q.bind(CHOICES, arg.getChoices());
                        q.bind(URL, arg.getPlayUrl());
                        q.bind(SONG_NAME, arg.getSongName());
                        q.bind(ARTIST_NAME, arg.getArtistName());
                    }
                };
            }
        }
    }
}
