package com.agileengine.quiz.analizer.chain;

import com.agileengine.quiz.analizer.model.SearchCreteria;
import org.w3c.dom.Node;

public class DefaultXMLAnalyzer implements XMLAnalyzerChain {

    @Override
    public int find(Node node, SearchCreteria searchCreteria, int counter) {
        return counter;
    }
}
