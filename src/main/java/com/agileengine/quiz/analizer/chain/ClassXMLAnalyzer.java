package com.agileengine.quiz.analizer.chain;

import com.agileengine.quiz.analizer.model.SearchCreteria;
import org.w3c.dom.Node;

import java.util.Objects;

public class ClassXMLAnalyzer implements XMLAnalyzerChain {
    private static final String CLASS_ATTRIBUTE_NAME = "class";
    private XMLAnalyzerChain next;

    public ClassXMLAnalyzer(XMLAnalyzerChain xmlAnalyzerChain) {
        this.next = xmlAnalyzerChain;
    }

    @Override
    public int find(Node node, SearchCreteria searchCreteria, int counter) {
        if (Objects.nonNull(node.getAttributes()) &&
                Objects.nonNull(node.getAttributes().getNamedItem(CLASS_ATTRIBUTE_NAME))) {
            String nodeValue = node.getAttributes().getNamedItem(CLASS_ATTRIBUTE_NAME).getNodeValue();
            if (Objects.equals(nodeValue, searchCreteria.getClassName())) {
                return next.find(node, searchCreteria, ++counter);
            }
        }
        return next.find(node, searchCreteria, counter);
    }
}
