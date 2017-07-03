package com.zfbpaysdk.pay.starzfbsdk.ok;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ASUS on 2017/6/13.
 */
public class XmlUtils {
    public static Map doXMLParse(String resultXml) {
        //解析返回的xml字符串，生成document对象
        Document document = null;
        try {
            document = DocumentHelper.parseText(resultXml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
//根节点
        Element root = document.getRootElement();
//子节点
        List<Element> childElements = root.elements();

        Map<String, Object> mapEle = new HashMap<String, Object>();
//遍历子节点
        mapEle = getAllElements(childElements, mapEle);
        return mapEle;
    }

    public static Map<String, Object> getAllElements(List<Element> childElements,Map<String,Object> mapEle) {
        for (Element ele : childElements) {
            mapEle.put(ele.getName(), ele.getText());
            if(ele.elements().size()>0){
                mapEle = getAllElements(ele.elements(), mapEle);
            }
        }
        return mapEle;
    }
}
