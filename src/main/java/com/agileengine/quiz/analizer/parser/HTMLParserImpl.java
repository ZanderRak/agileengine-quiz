package com.agileengine.quiz.analizer.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static javax.xml.xpath.XPathConstants.NODESET;

public class HTMLParserImpl implements HTMLParserable {
    private DocumentBuilderFactory factory;
    private XPathFactory xpathFactory;

    private static final String FIND_ELEMENTS_BY_ATTRIBUTE_VALUE = "//*[@%s='%s']";

    private static final String FIND_ELEMENT_BY_ID_XPATH = "//*[@id='%s']";

    public HTMLParserImpl(DocumentBuilderFactory factory, XPathFactory xPathFactory) {
        this.factory = factory;
        this.xpathFactory = xPathFactory;
    }

    @Override
    public Optional<Document> parse(String path) {
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return Optional.of(builder.parse(path));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.out.println("File not found");
        }
        return Optional.empty();
    }

    @Override
    public Node findElementById(Document document, String id) {
        try {
            XPath xPath = xpathFactory.newXPath();
            XPathExpression expr = xPath.compile(getElementByIdXPathQuery(id));
            NodeList nodes = (NodeList) expr.evaluate(document, NODESET);
            if (nodes.getLength() > 1) {
                throw new IllegalStateException("Id is not unique");
            }
            if (nodes.getLength() == 0) {
                throw new IllegalStateException(format("Element with Id %s was not found", id));
            }
            return nodes.item(0);
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Parsing failed");
        }
    }

    @Override
    public List<Node> findElementsByAttribute(Document document, String attribute, String value) {
        try {
            XPath xPath = xpathFactory.newXPath();
            return Optional.of(xPath.compile(getElementsByAttributeValueXPathQuery(attribute, value)))
                    .map(x -> mapXPathToObject(document, x))
                    .filter(NodeList.class::isInstance)
                    .map(NodeList.class::cast)
                    .map(x -> IntStream.range(0, x.getLength()).mapToObj(x::item))
                    .map(x -> x.collect(toList()))
                    .orElse(emptyList());
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Parsing failed");
        }
    }

    private Object mapXPathToObject(Document document, XPathExpression xPathExpression) {
        try {
            return xPathExpression.evaluate(document, NODESET);
        } catch (XPathExpressionException e) {
            throw new IllegalStateException(e);
        }
    }

    private static String getElementByIdXPathQuery(String elementId) {

        return format(FIND_ELEMENT_BY_ID_XPATH, elementId);
    }

    private static String getElementsByAttributeValueXPathQuery(String attribute, String value) {

        return format(FIND_ELEMENTS_BY_ATTRIBUTE_VALUE, attribute, value);
    }
}