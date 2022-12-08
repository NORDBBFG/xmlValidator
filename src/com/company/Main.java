package com.company;

public class Main {

    public static void main(String[] args) {

        String xmlExample = "<tag>hello<tag2>hello</tag2><tag3></tag3></tag>";


        Validator validator = new Validator();
        System.out.println(xmlExample + "\n" + validator.ValidateXml(xmlExample));

    }
}
