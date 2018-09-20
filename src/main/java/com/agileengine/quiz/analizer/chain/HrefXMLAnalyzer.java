package com.agileengine.quiz.analizer.chain;

import com.agileengine.quiz.analizer.model.SearchCreteria;
import org.w3c.dom.Node;

import java.util.Objects;

public class HrefXMLAnalyzer implements XMLAnalyzerChain {
    private static final String HREF_ATTRIBUTE_NAME = "href";
    private XMLAnalyzerChain next;

    public HrefXMLAnalyzer(XMLAnalyzerChain xmlAnalyzerChain) {
        this.next = xmlAnalyzerChain;
    }

    @Override
    public int find(Node node, SearchCreteria searchCreteria, int counter) {
        if (Objects.nonNull(node.getAttributes()) &&
                Objects.nonNull(node.getAttributes().getNamedItem(HREF_ATTRIBUTE_NAME))) {
            String nodeValue = node.getAttributes().getNamedItem(HREF_ATTRIBUTE_NAME).getNodeValue();
            if (nodeValue != null && nodeValue.contains(searchCreteria.getHref())) {
                return next.find(node, searchCreteria, ++counter);
            }
        }
        return next.find(node, searchCreteria, counter);
    }
}
