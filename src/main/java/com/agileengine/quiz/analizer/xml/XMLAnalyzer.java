package com.agileengine.quiz.analizer.xml;

import com.agileengine.quiz.analizer.model.SearchCreteria;
import org.w3c.dom.Node;

import java.util.Map;

public interface XMLAnalyzer {
    Map<Node, Integer> find(Node document, SearchCreteria searchCreteria);
}
