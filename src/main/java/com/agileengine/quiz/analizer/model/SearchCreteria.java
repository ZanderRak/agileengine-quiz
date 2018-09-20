package com.agileengine.quiz.analizer.model;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Objects;

public class SearchCreteria {
    private String id;
    private String title;
    private String className;
    private String href;

    public SearchCreteria(Node node) {
        Element e = (Element) node;
        this.id = e.getAttribute("id");
        this.title = e.getAttribute("title");
        this.className = e.getAttribute("class");
        this.href = e.getAttribute("href");
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getClassName() {
        return className;
    }

    public String getHref() {
        return href;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchCreteria that = (SearchCreteria) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(className, that.className) &&
                Objects.equals(href, that.href);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, className, href);
    }
}
