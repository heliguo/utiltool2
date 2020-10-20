//package com.example.utiltool2.util;
//
//import java.io.IOException;
//import java.io.Reader;
//import java.io.StringReader;
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author lgh on 2020/10/12:17:46
// * @description
// */
//public class XmlUtils {
//
//    /**
//     * <P>
//     * map集合转化成xml字符串
//     * </P>
//     *
//     * @param map
//     * @param rootName
//     * @return
//     */
//    public static String mapToXml(Map<String, String> map, String rootName) {
//        Element root = new Element(rootName);
//        if (map == null)
//            return xmlToString(root);
//        for (String str : map.keySet())
//            root.addContent(new Element(str).setText((map.get(str) == null ? "" : map.get(str) + "")));
//        return xmlToString(root);
//    }
//
//    /**
//     * <P>
//     * xml字符串转化成map集合
//     * </P>
//     *
//     * @param xmlStr字符串
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    public static Map<String, String> xmlToMap(String xmlStr) {
//
//        SAXBuilder builder = new SAXBuilder();
//        Map<String, String> map = new HashMap<String, String>();
//        try {
//            xmlStr = URLDecoder.decode(xmlStr, "UTF-8");
//            Reader in = new StringReader(xmlStr);
//            Document doc = builder.build(in);
//            Element root = doc.getRootElement();
//            List<Element> list = root.getChildren();
//            for (Element e : list)
//                map.put(e.getName(), e.getText());
//            return map;
//        } catch (JDOMException e) {
//        } catch (UnsupportedEncodingException e) {
//        } catch (IOException e) {
//        } catch (Exception e) {
//        }
//        return map;
//
//    }
//
//
//}
