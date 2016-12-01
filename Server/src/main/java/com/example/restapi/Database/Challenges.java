package com.example.restapi.Database;

import com.example.restapi.Model.Challenge;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.lang.annotation.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ikbalkazar on 29/11/16.
 */
public interface Challenges {
    String ID = "id";
    String SENDER = "sender";
    String RECEIVER = "receiver";
    String CREATED_AT = "createdat";
    String SENDER_SCORE = "senderscore";
    String RECEIVER_SCORE = "receiverscore";
    String STATUS = "status";

    @SqlQuery("SELECT COUNT(*) FROM challenges")
    int generateId();

    @Mapper(ChallengeMapper.class)
    @SqlQuery("SELECT * FROM challenges WHERE " + ID + " = :id")
    Challenge getById(@Bind("id") String id);

    @Mapper(ChallengeMapper.class)
    @SqlQuery("SELECT * FROM challenges WHERE " + SENDER + " = :me OR " + RECEIVER + " = :me")
    List<Challenge> getFor(@Bind("me") String me);

    @SqlUpdate("INSERT INTO challenges (" + ID + "," + SENDER + "," + RECEIVER + "," + CREATED_AT + ","
            + SENDER_SCORE + "," + RECEIVER_SCORE + "," + STATUS + ") VALUES (:" + ID + ",:" + SENDER + ",:" + RECEIVER
            + ",:" + CREATED_AT  + ",:" + SENDER_SCORE + ",:" + RECEIVER_SCORE + ",:" + STATUS + ")")
    void insertChallenge(@BindBean("challenge") Challenge challenge);

    @SqlUpdate("UPDATE challenges SET " + STATUS + " = :status WHERE " + ID + " = :id")
    void setStatus(@Bind("status") int status, @Bind("id") String id);

    @SqlUpdate("UPDATE challenges SET " + SENDER_SCORE + " = :score WHERE " + ID + " = :id")
    void setSenderScore(@Bind("id") String id, @Bind("score") int score);

    @SqlUpdate("UPDATE challenges SET " + RECEIVER_SCORE + " = :score WHERE " + ID + " = :id")
    void setReceiverScore(@Bind("id") String id, @Bind("score") int score);

    class ChallengeMapper implements ResultSetMapper<Challenge> {
        @Override
        public Challenge map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new Challenge(resultSet.getString(ID),
                    resultSet.getString(SENDER),
                    resultSet.getString(RECEIVER),
                    resultSet.getString(CREATED_AT),
                    resultSet.getInt(SENDER_SCORE),
                    resultSet.getInt(RECEIVER_SCORE),
                    resultSet.getInt(STATUS));
        }
    }

    @BindingAnnotation(Challenges.BindChallenge.ChallengeBinderFactory.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER})
    @interface BindChallenge {
        class ChallengeBinderFactory implements BinderFactory {
            public Binder build(Annotation annotation) {
                return new Binder<Challenges.BindChallenge, Challenge>() {
                    public void bind(SQLStatement q, Challenges.BindChallenge bind, Challenge arg) {
                        q.bind(ID, arg.getId());
                        q.bind(SENDER, arg.getSender());
                        q.bind(RECEIVER, arg.getReceiver());
                        q.bind(CREATED_AT, arg.getCreatedAt());
                        q.bind(SENDER_SCORE, arg.getSenderScore());
                        q.bind(RECEIVER_SCORE, arg.getRecieverScore());
                        q.bind(STATUS, arg.getStatus());
                    }
                };
            }
        }
    }
}
