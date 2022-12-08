package com.company;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public String ValidateXml(String xml1){

        if(xml1.isEmpty()) return "XML string is empty";

        String result = "XML is valid";
        ArrayList<String> openTags = new ArrayList<>();
        ArrayList<String> closeTags = new ArrayList<>();

        Pattern p = Pattern.compile("\\<.*?\\>");
        Pattern self = Pattern.compile("\\<.*?\\/>");
        ArrayList<String> tagsGuts = new ArrayList<>();
        ArrayList<String> selfTagsGuts = new ArrayList<>();

        while(xml1.contains("<") && xml1.contains("/>")) {
            Matcher m = self.matcher(xml1);
            if(m.find())
                selfTagsGuts.add((String) m.group().subSequence(1, m.group().length()-1));

            xml1 = xml1.replaceFirst("\\<.*?\\/>", "");
        }

        while(xml1.contains("<") && xml1.contains(">")) {
            Matcher m = p.matcher(xml1);
            if(m.find())
                tagsGuts.add((String) m.group().subSequence(1, m.group().length()-1));

            xml1 = xml1.replaceFirst("\\<.*?\\>", "");
        }

        Pattern selfPattern = Pattern.compile("[А-Яа-я]");

        for (int i = 0; i< selfTagsGuts.size(); i++)
        {
            Matcher matcher = selfPattern.matcher(selfTagsGuts.get(i));
            Character firstChar = selfTagsGuts.get(i).toCharArray()[0];

            if (firstChar.equals(' '))
            {
                result = "Invalid XML : Extra space <_";
                break;
            }
            if (matcher.find()){
                result = "Invalid XML : <Only English and symbols> required inside teg";
                break;
            }
        }

        for (int i = 0; i< tagsGuts.size(); i++)
        {
            Matcher matcher = selfPattern.matcher(tagsGuts.get(i));
            Character firstChar = tagsGuts.get(i).toCharArray()[0];

            if (firstChar.equals(' '))
            {
                result = "Invalid XML : Extra space <_";
                break;
            }
            if (matcher.find()){
                result = "Invalid XML : <Only English and symbols> required inside teg";
                break;
            }

            if(firstChar.equals('/'))
                closeTags.add(tagsGuts.get(i));
            else {
                openTags.add(tagsGuts.get(i));}

            if (i == tagsGuts.size() - 1) {
                if (closeTags.size() != openTags.size()){
                    result = "Invalid XML : The number of opening and closing tags is not equal";
                    break;
                }

                int massiveLength = tagsGuts.size();
                int counter;
                do {
                    counter = 0;
                    for (int j = 0; j < tagsGuts.size() - 1; j++) {

                        String secondTage;
                        String firstTage = tagsGuts.get(j);
                        secondTage = tagsGuts.get(j + 1).replaceFirst("[/]", "");
                        if (Objects.equals(firstTage, secondTage)){
                            tagsGuts.remove(j);
                            tagsGuts.remove(j);
                            counter++;
                            massiveLength = massiveLength - 2;
                        }
                    }
                    if (tagsGuts.size() == 0)
                        break;
                } while (counter !=0);

                if (tagsGuts.size() != 0)
                    result = "Invalid XML : tag order is wrong";

            }
        }

        return result;
    }
}