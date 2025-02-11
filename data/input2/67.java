class _a {public static void main (String args []) throws ParserConfigurationException, SAXException, IOException {
    Document document = ParseXML2Dom.parse (new File ("/home/jeff/test.xml"));
    NodeList nodeList = document.getElementsByTagName ("*");
    for (int i = 0;
    i < nodeList.getLength (); i ++) {
        Node node = nodeList.item (i);
        if (node.getNodeType () == Node.ELEMENT_NODE) {
            System.out.println (node.getNodeName ());
            System.out.println (node.getTextContent ());
        }
    }
}
}