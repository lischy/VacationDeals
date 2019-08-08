package com.example.vacationdeals;

import android.os.Parcel;
import android.os.Parcelable;

public class RichTravelDeal implements Parcelable {
    private  String id;
    private String description;
    private String title;
    private String price;
    private String imageURL;

    public RichTravelDeal(){};

    public RichTravelDeal( String description, String title, String price, String imageURL) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.price = price;
        this.imageURL = imageURL;
    }

    protected RichTravelDeal(Parcel in) {
        id = in.readString();
        description = in.readString();
        title = in.readString();
        price = in.readString();
        imageURL = in.readString();
    }

    public static final Creator<RichTravelDeal> CREATOR = new Creator<RichTravelDeal>() {
        @Override
        public RichTravelDeal createFromParcel(Parcel in) {
            return new RichTravelDeal(in);
        }

        @Override
        public RichTravelDeal[] newArray(int size) {
            return new RichTravelDeal[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(description);
        parcel.writeString(title);
        parcel.writeString(price);
        parcel.writeString(imageURL);
    }
}
