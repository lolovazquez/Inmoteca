package com.example.inmoteca.model;

public class Photo {

    private String id;
    private Property property;
    private String imgurLink;
    private String deletehash;

    public Photo() {
    }

    public Photo(String id, Property property, String imgurLink, String deletehash) {
        this.id = id;
        this.property = property;
        this.imgurLink = imgurLink;
        this.deletehash = deletehash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getImgurLink() {
        return imgurLink;
    }

    public void setImgurLink(String imgurLink) {
        this.imgurLink = imgurLink;
    }

    public String getDeletehash() {
        return deletehash;
    }

    public void setDeletehash(String deletehash) {
        this.deletehash = deletehash;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", property=" + property +
                ", imgurLink='" + imgurLink + '\'' +
                ", deletehash='" + deletehash + '\'' +
                '}';
    }
}
