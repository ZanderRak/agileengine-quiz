package com.agileengine.quiz.analizer;

import com.agileengine.quiz.analizer.chain.ClassXMLAnalyzer;
import com.agileengine.quiz.analizer.chain.DefaultXMLAnalyzer;
import com.agileengine.quiz.analizer.chain.HrefXMLAnalyzer;
import com.agileengine.quiz.analizer.chain.IdXMLAnalyzer;
import com.agileengine.quiz.analizer.chain.TitleXMLAnalyzer;
import com.agileengine.quiz.analizer.model.SearchCreteria;
import com.agileengine.quiz.analizer.parser.HTMLParserImpl;
import com.agileengine.quiz.analizer.parser.HTMLParserable;
import com.agileengine.quiz.analizer.xml.XMLAnalyzer;
import com.agileengine.quiz.analizer.xml.XMLAnalyzerImpl;
import com.agileengine.quiz.analizer.xml.XpathBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathFactory;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Demo {
    private HTMLParserable htmlParserable;
    private XMLAnalyzer xmlAnalyzer;
    private XpathBuilder xpathBuilder;

    public Demo() {
        TitleXMLAnalyzer titleXMLAnalyzer = new TitleXMLAnalyzer(new DefaultXMLAnalyzer());
        HrefXMLAnalyzer hrefXMLAnalyzer = new HrefXMLAnalyzer(titleXMLAnalyzer);
        ClassXMLAnalyzer classXMLAnalyzer = new ClassXMLAnalyzer(hrefXMLAnalyzer);
        IdXMLAnalyzer idXMLAnalyzer = new IdXMLAnalyzer(classXMLAnalyzer);
        xmlAnalyzer = new XMLAnalyzerImpl(idXMLAnalyzer);
        htmlParserable = new HTMLParserImpl(DocumentBuilderFactory.newDefaultInstance(),
                XPathFactory.newInstance());
        xpathBuilder = new XpathBuilder();
    }

    public void process(String pathToFirst, String pathToSecond, String attribute, String attributeValue) {
        Optional<Document> standart = htmlParserable.parse(pathToFirst);
        Optional<Document> test = htmlParserable.parse(pathToSecond);
        if (standart.isPresent() && test.isPresent()) {
            Node node = null;
            if (Objects.nonNull(attribute) && Objects.nonNull(attributeValue)) {
                List<Node> nodes = htmlParserable.findElementsByAttribute(standart.get(), attribute, attributeValue);
                if (!nodes.isEmpty()) {
                    node = nodes.get(0);
                }
            }
            SearchCreteria searchCreteria = new SearchCreteria(node);
            show(xmlAnalyzer.find(test.get(), searchCreteria));
        }
    }

    public void process(String pathToFirst, String pathToSecond, String id) {
        Optional<Document> standart = htmlParserable.parse(pathToFirst);
        Optional<Document> test = htmlParserable.parse(pathToSecond);
        if (standart.isPresent() && test.isPresent()) {
            Node node = null;
            if (Objects.nonNull(id)) {
                node = htmlParserable.findElementById(standart.get(), id);
            }
            SearchCreteria searchCreteria = new SearchCreteria(node);
            show(xmlAnalyzer.find(test.get(), searchCreteria));
        }
    }

    private void show(Map<Node, Integer> nodeIntegerMap) {
        System.out.println("Most similar first");
        nodeIntegerMap.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .forEach(node -> System.out.println(xpathBuilder.build(node)));
    }
}
