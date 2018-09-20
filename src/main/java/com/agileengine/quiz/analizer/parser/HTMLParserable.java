package com.agileengine.quiz.analizer.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.List;
import java.util.Optional;

public interface HTMLParserable {
    Optional<Document> parse(String path);

    Node findElementById(Document document, String id);

    List<Node> findElementsByAttribute(Document document, String attribute, String value);

}
