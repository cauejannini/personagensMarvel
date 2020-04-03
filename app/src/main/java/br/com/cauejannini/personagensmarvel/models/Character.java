package br.com.cauejannini.personagensmarvel.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Character implements Serializable {

    private int id;
    private String name;
    private String description;
    private Object modified; //Date??
    private String resourceURI;
    private List<MarvelURL> urls;
    private MarvelImage thumbnail;
    private Object comics;
    private Object stories;
    private Object events;
    private Object series;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getModified() {
        return modified;
    }

    public void setModified(Object modified) {
        this.modified = modified;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public List<MarvelURL> getUrls() {
        return urls;
    }

    public void setUrls(List<MarvelURL> urls) {
        this.urls = urls;
    }

    public MarvelImage getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(MarvelImage thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Object getComics() {
        return comics;
    }

    public void setComics(Object comics) {
        this.comics = comics;
    }

    public Object getStories() {
        return stories;
    }

    public void setStories(Object stories) {
        this.stories = stories;
    }

    public Object getEvents() {
        return events;
    }

    public void setEvents(Object events) {
        this.events = events;
    }

    public Object getSeries() {
        return series;
    }

    public void setSeries(Object series) {
        this.series = series;
    }

}
