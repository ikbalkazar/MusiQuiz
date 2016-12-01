package com.example.restapi.Database;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import java.util.List;

/**
 * Created by ikbalkazar on 29/11/16.
 */
public interface Friends {
    String ME = "user1";
    String HER = "user2";

    @SqlQuery("SELECT DISTINCT " + HER + " FROM friends WHERE " + ME + " = :me")
    List<String> getFriendsOf(@Bind("me") String me);

    @SqlUpdate("INSERT INTO friends (" + ME + "," + HER + ")" + " VALUES (:me, :her)")
    void addFriend(@Bind("me") String me, @Bind("her") String her);

    @SqlUpdate("DELETE FROM friends WHERE " + ME + " = :me AND " + HER + " = :her")
    void removeFriend(@Bind("me") String me, @Bind("her") String her);
}
