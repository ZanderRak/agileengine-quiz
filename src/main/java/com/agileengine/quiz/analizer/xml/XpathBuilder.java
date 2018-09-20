package com.agileengine.quiz.analizer.xml;

import org.w3c.dom.Node;

import java.util.Objects;

public class XpathBuilder {
    private static final String EMPTY_STRING = "";

    public String build(Node node) {
        Node current = node;
        StringBuilder output = new StringBuilder(EMPTY_STRING);
        while (Objects.nonNull(current.getParentNode())) {
            Node parent = current.getParentNode();
            if (Objects.nonNull(parent) && parent.getChildNodes().getLength() > 1) {
                int numberChild = 1;
                Node siblingSearch = current;
                while ((siblingSearch = siblingSearch.getPreviousSibling()) != null) {
                    if (siblingSearch.getNodeName().equals(current.getNodeName())) {
                        numberChild++;
                    }
                }
                output.insert(0, "/" + current.getNodeName() + "[" + numberChild + "]");
            } else {
                output.insert(0, "/" + current.getNodeName());
            }
            current = current.getParentNode();
        }
        return output.toString();
    }
}
