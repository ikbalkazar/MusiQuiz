package com.example.restapi.Database;

import com.example.restapi.Model.Question;
import com.example.restapi.Model.Song;
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
public interface Songs {
    String ID = "id";
    String NAME = "name";
    String ARTIST = "artist";
    String URL = "url";

    @SqlQuery("SELECT COUNT(*) FROM songs;")
    int songCount();

    @Mapper(SongMapper.class)
    @SqlQuery("SELECT * FROM songs WHERE " + ID + " = :id")
    Song getSong(@Bind("id") String id);

    @SqlUpdate("INSERT INTO songs (" + ID + "," + URL + "," + NAME + "," + ARTIST + ") " +
                "VALUES (:" + ID  + ",:" + URL + ",:" + NAME + ",:" + ARTIST + ")")
    void addSong(@BindSong Song song);

    class SongMapper implements ResultSetMapper<Song> {
        @Override
        public Song map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new Song(resultSet.getString(ID),
                    resultSet.getString(NAME),
                    resultSet.getString(ARTIST),
                    resultSet.getString(URL));
        }
    }

    @BindingAnnotation(BindSong.SongBinderFactory.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER})
    @interface BindSong {
        class SongBinderFactory implements BinderFactory {
            public Binder build(Annotation annotation) {
                return new Binder<BindSong, Song>() {
                    public void bind(SQLStatement q, BindSong bind, Song arg) {
                        q.bind(ID, arg.getId());
                        q.bind(URL, arg.getUrl());
                        q.bind(NAME, arg.getName());
                        q.bind(ARTIST, arg.getArtist());
                    }
                };
            }
        }
    }
}
