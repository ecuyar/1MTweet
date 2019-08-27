package edu.ceng;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;

import java.io.*;
import java.util.*;

public class App
{
    public static void main( String[] args ) throws IOException
    {
        Long startTime=System.currentTimeMillis();
        String entity="";
        boolean reverse=false;
        boolean ignoreCase=false;
        int number=0;
        String fileName="";
        try {
            CommandArgs result = CliFactory.parseArguments(CommandArgs.class, args);
            entity = result.getEntity();
            reverse = result.getIsReverse();
            ignoreCase  = result.getIsIgnore();
            number= result.getNumber();
             fileName = result.getFileName();

        } catch (ArgumentValidationException e){
            System.out.println(e);
        }



        //System.out.println("Please wait just a seconds...");

        // long startTime = System.currentTimeMillis();

        Map<String ,Integer> topics = Collections.synchronizedMap(new HashMap<String ,Integer>());
        Map<String ,Integer> mentions = Collections.synchronizedMap(new HashMap<String ,Integer>());


        File file = new File(fileName);
        if(file.exists()){
            BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file), "windows-1254"));
           

            ///StringBuilder line = new StringBuilder(reader.readLine());
            String line =reader.readLine();
            String[] word;
            //reading file
            String currentWord="";
            while(line!=null){
                word =line.split(" ");
                if(word.length>9){
                    if(word[6].equals("-")&&word[8].equals("-")){
                        for(int i=9;i<word.length;i++){
                            currentWord=word[i];
                            if(ignoreCase){
                             currentWord=currentWord.toLowerCase();
                            }

                            if(currentWord.length()!=0&&currentWord.length()!=1){
                                //seperating words as 'mentions' and 'hashtags' and adding them to maps

                                if (currentWord.charAt(0) == '#') {
                                    if(!currentWord.substring(1).contains("#")){
                                        if(!currentWord.substring(1).contains("@")){
                                            if (topics.containsKey(currentWord)) {
                                                topics.put(currentWord, (topics.get(currentWord)) + 1);
                                            } else {
                                                topics.put(currentWord, 1);
                                            }
                                        }
                                    }

                                }
                                if (currentWord.charAt(0) == '@') {
                                    if(!currentWord.substring(1).contains("#")){
                                        if(!currentWord.substring(1).contains("@")){
                                            if (mentions.containsKey(currentWord)) {
                                                mentions.put(currentWord, (mentions.get(currentWord)) + 1);
                                            } else {
                                                mentions.put(currentWord, 1);
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }



                //line.setLength(0);
                //line.append(reader.readLine());

                line = reader.readLine();

            }
            //sorting maps
            List<Map.Entry<String,Integer>> hashtagList=  sortByValue(topics,reverse);
            List<Map.Entry<String,Integer>> mentionList=  sortByValue(mentions,reverse);
            //printing result
            System.out.println();
            System.out.println("Top Topics");

            if(entity.equalsIgnoreCase("mention")){
             for(int i=0;i<number;i++){
                 System.out.println(mentionList.get(i).getKey()+"   "+mentionList.get(i).getValue());
             }

            }
            else if( entity.equals("")||entity.equalsIgnoreCase("hashtag")){

                for(int i=0;i<number;i++){
                    System.out.println(hashtagList.get(i).getKey()+"   "+hashtagList.get(i).getValue());
                }
            }
            else {
                System.out.println("Error occured while choosing entity");
            }


            System.out.println("Süre : " + (System.currentTimeMillis() - startTime));


        }
        else {
            System.out.println("file didn't find!");
        }




    }
    //method for sorting map by value of entries
    public static List<Map.Entry<String,Integer>> sortByValue(Map<String,Integer> map,boolean reverse){
        Comparator comparator;
        if(reverse){
             comparator = new Comparator<Map.Entry<String,Integer>>(){
                public int compare(Map.Entry<String,Integer> x,
                                   Map.Entry<String,Integer> y){
                    return (x.getValue()).compareTo(y.getValue());
                }
            };
        }
        else{
             comparator = new Comparator<Map.Entry<String,Integer>>(){
                public int compare(Map.Entry<String,Integer> x,
                                   Map.Entry<String,Integer> y){
                    return (y.getValue()).compareTo(x.getValue());
                }
            };
        }
        Set<Map.Entry<String,Integer>> set= map.entrySet();
        List<Map.Entry<String,Integer>> list=new ArrayList<Map.Entry<String,Integer>>(set);
        Collections.sort(list,comparator);
        return list;
    }
}
