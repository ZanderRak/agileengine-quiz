package com.agileengine.quiz.analizer.chain;

import com.agileengine.quiz.analizer.model.SearchCreteria;
import org.w3c.dom.Node;

import java.util.Objects;

public class TitleXMLAnalyzer implements XMLAnalyzerChain {
    private static final String TITLE_ATTRIBUTE_NAME = "title";
    private XMLAnalyzerChain next;

    public TitleXMLAnalyzer(XMLAnalyzerChain xmlAnalyzerChain) {
        this.next = xmlAnalyzerChain;
    }

    @Override
    public int find(Node node, SearchCreteria searchCreteria, int counter) {
        if (Objects.nonNull(node.getAttributes()) &&
                Objects.nonNull(node.getAttributes().getNamedItem(TITLE_ATTRIBUTE_NAME))) {
            String nodeValue = node.getAttributes().getNamedItem(TITLE_ATTRIBUTE_NAME).getNodeValue();
            if (nodeValue != null && nodeValue.contains(searchCreteria.getTitle())) {
                return next.find(node, searchCreteria, ++counter);
            }
        }
        return next.find(node, searchCreteria, counter);
    }
}
