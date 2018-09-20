package com.agileengine.quiz.analizer.chain;

import com.agileengine.quiz.analizer.model.SearchCreteria;
import org.w3c.dom.Node;

import java.util.Objects;

public class IdXMLAnalyzer implements XMLAnalyzerChain {
    private static final String ID_ATTRIBUTE_NAME = "id";
    private XMLAnalyzerChain next;

    public IdXMLAnalyzer(XMLAnalyzerChain xmlAnalyzerChain) {
        this.next = xmlAnalyzerChain;
    }

    @Override
    public int find(Node node, SearchCreteria searchCreteria, int counter) {
        if (Objects.nonNull(node.getAttributes()) &&
                Objects.nonNull(node.getAttributes().getNamedItem(ID_ATTRIBUTE_NAME))) {
            String nodeValue = node.getAttributes().getNamedItem(ID_ATTRIBUTE_NAME).getNodeValue();
            if (Objects.equals(nodeValue, searchCreteria.getId())) {
                return next.find(node, searchCreteria, ++counter);
            }
        }
        return next.find(node, searchCreteria, counter);
    }
}
