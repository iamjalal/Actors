package com.paytouch.jalal.actors.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jalalsouky on 04/01/15.
 */
public class Actor implements Parcelable {

    public final String name;
    public final double popularity;
    public final String profilePath;
    public final int id;
    public final String location;
    public final String description;
    public final List<Film> filmography;
    public final boolean top;

    private class JsonProperties {
        private static final String NAME = "name";
        private static final String POPULARITY = "popularity";
        private static final String PROFILE_PATH = "profile_path";
        private static final String ID = "identifier";
        private static final String LOCATION = "location";
        private static final String DESCRIPTION = "description";
        private static final String KNOWN_FOR = "known_for";
        private static final String TOP = "top";
    }

    public Actor(JSONObject data) throws JSONException {
        name = data.optString(JsonProperties.NAME);
        popularity = data.optDouble(JsonProperties.POPULARITY);
        profilePath = data.optString(JsonProperties.PROFILE_PATH);
        id = data.optInt(JsonProperties.ID);
        location = data.optString(JsonProperties.LOCATION);
        description = data.optString(JsonProperties.DESCRIPTION);
        filmography = parseFilms(data.getJSONArray(JsonProperties.KNOWN_FOR));
        top = data.optBoolean(JsonProperties.TOP);
    }

    public Actor(Parcel in) {
        name = in.readString();
        popularity = in.readDouble();
        profilePath = in.readString();
        id = in.readInt();
        location = in.readString();
        description = in.readString();
        top = in.readByte() != 0;

        filmography = new ArrayList<Film>();
        in.readList(filmography, Film.class.getClassLoader());
    }

    public Actor(String name, String location, boolean top, double popularity) {
        this.name = name;
        this.location = location;
        this.top = top;
        this.popularity = popularity;
        this.profilePath = null;
        this.id = 0;
        this.description = null;
        this.filmography = null;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(popularity);
        dest.writeString(profilePath);
        dest.writeInt(id);
        dest.writeString(location);
        dest.writeString(description);
        dest.writeByte((byte)(top ? 1 : 0));
        dest.writeList(filmography);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Actor createFromParcel(Parcel in) {
            return new Actor(in);
        }

        public Actor[] newArray(int size) {
            return new Actor[size];
        }
    };

    private List<Film> parseFilms(JSONArray array) throws JSONException {
        List<Film> films = new ArrayList<Film>();
        for(int i=0; i < array.length(); i++) {
            Film film = new Film(array.optJSONObject(i));
            films.add(film);
        }

        return films;
    }

    public static Comparator<Actor> COMPARE_BY_NAME = new Comparator<Actor>() {
        public int compare(Actor one, Actor other) {
            return one.name.compareTo(other.name);
        }
    };

    public static Comparator<Actor> COMPARE_BY_POPULARITY = new Comparator<Actor>() {
        public int compare(Actor one, Actor other) {
            return ((Double)one.popularity).compareTo(other.popularity) * -1;
        }
    };
}