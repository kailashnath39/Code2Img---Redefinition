class _a {public static Document parse (File xmlFile) throws ParserConfigurationException, SAXException, IOException {
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance ();
    docFactory.setValidating (false);
    docFactory.setNamespaceAware (true);
    docFactory.setExpandEntityReferences (false);
    DocumentBuilder parser = docFactory.newDocumentBuilder ();
    Document document = parser.parse (xmlFile);
    return document;
}
}