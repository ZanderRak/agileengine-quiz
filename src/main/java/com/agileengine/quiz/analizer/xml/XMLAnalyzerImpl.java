package com.agileengine.quiz.analizer.xml;

import com.agileengine.quiz.analizer.chain.XMLAnalyzerChain;
import com.agileengine.quiz.analizer.model.SearchCreteria;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

public class XMLAnalyzerImpl implements XMLAnalyzer {
    private XMLAnalyzerChain xmlAnalyzerChain;

    public XMLAnalyzerImpl(XMLAnalyzerChain xmlAnalyzerChain) {
        this.xmlAnalyzerChain = xmlAnalyzerChain;
    }

    @Override
    public Map<Node, Integer> find(Node document, SearchCreteria searchCreteria) {
        Map<Node, Integer> nodeMatches = new HashMap<>();
        NodeList nodeList = document.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            int matches = xmlAnalyzerChain.find(currentNode, searchCreteria, 0);
            if (matches > 0) {
                nodeMatches.put(currentNode, matches);
            }
            nodeMatches.putAll(find(currentNode, searchCreteria));
        }
        return nodeMatches;
    }
}
