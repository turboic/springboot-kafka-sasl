package com.example.cloud.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author root
 */
public class CamelUtil {
    public static String convert(String str) {
        StringBuilder sb = new StringBuilder();
        char[] chars = str.toCharArray();
        for (char ch : chars) {
            if (Character.isUpperCase(ch)) {
                sb.append("_").append((ch + "").toLowerCase());
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    public static List retainAll(Collection collection, Collection retain) {
        List list = new ArrayList(Math.min(collection.size(), retain.size()));

        for (Iterator iter = collection.iterator(); iter.hasNext();) {
            Object obj = iter.next();
            if (retain.contains(obj)) {
                list.add(obj);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        List<String> l1 =new ArrayList<>();
        l1.add("1");
        l1.add("2");
        l1.add("3");

        List<String> l2 =new ArrayList<>();
        l2.add("3");
        l2.add("4");
        l2.add("5");

        List l3 = retainAll(l1,l2);
        System.err.println(l3.size());

    }
}
