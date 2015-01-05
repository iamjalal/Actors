package com.paytouch.jalal.actors.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jalalsouky on 04/01/15.
 */
public class Film implements Parcelable {

    public final int id;
    public final String title;
    public final long releaseDate;
    public final double voteAverage;
    public final int voteCount;
    public final String posterPath;

    private class JsonProperties {
        private static final String ID = "id";
        private static final String TITLE = "title";
        private static final String RELEASE_DATE = "release_date";
        private static final String VOTE_AVG = "vote_average";
        private static final String VOTE_COUNT = "vote_count";
        private static final String POSTER = "poster_path";
    }

    public Film(JSONObject data) throws JSONException {
        id = data.optInt(JsonProperties.ID);
        title = data.optString(JsonProperties.TITLE);
        releaseDate = data.optLong(JsonProperties.RELEASE_DATE);
        voteAverage = data.optDouble(JsonProperties.VOTE_AVG);
        voteCount = data.optInt(JsonProperties.VOTE_COUNT);
        posterPath = data.optString(JsonProperties.POSTER);
    }

    public Film(Parcel in) {
        id = in.readInt();
        title = in.readString();
        releaseDate = in.readLong();
        voteAverage = in.readDouble();
        voteCount = in.readInt();
        posterPath = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeLong(releaseDate);
        dest.writeDouble(voteAverage);
        dest.writeInt(voteCount);
        dest.writeString(posterPath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
}