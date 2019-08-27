
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException{
        System.out.println("Trending page is loading, please wait...");

        Map<String ,Integer> topics = Collections.synchronizedMap(new HashMap<String ,Integer>());
        Map<String ,Integer> mentions = Collections.synchronizedMap(new HashMap<String ,Integer>());

        File file = new File("C:/Users/MONSTER/Downloads/Compressed/all_tweets.txt");
        if(file.exists()){
            BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line =reader.readLine();
            String[] word;
            while(line!=null){
                word =line.split(" ");
                for(int i=0;i<word.length;i++){
                    if(word[i].length()!=0) {
                        if (word[i].charAt(0) == '#') {
                            if (topics.containsKey(word[i])) {
                                topics.put(word[i], (topics.get(word[i])) + 1);
                            } else {
                                topics.put(word[i], 1);
                            }
                        }
                        if (word[i].charAt(0) == '@') {
                            if (mentions.containsKey(word[i])) {
                                mentions.put(word[i], (mentions.get(word[i])) + 1);
                            } else {
                                mentions.put(word[i], 1);
                            }
                        }
                    }
                }
                line = reader.readLine();
            }
            List<Map.Entry<String,Integer>> hashtagList=sortByValue(topics);
            List<Map.Entry<String,Integer>> mentionList=sortByValue(mentions);

            System.out.println();
            System.out.println("Trending Topics Are:");

            int c=1;
            for(Map.Entry<String,Integer> entry :hashtagList){
                if(c>10){
                    break;
                }
                System.out.println(c + "-)" + entry.getKey());
                c++;
            }
            System.out.println();
            System.out.println( "Trending Mentions Are:");

            int co=1;
            for(Map.Entry<String,Integer> entry :mentionList){
                if(entry.getKey().equals("#")){
                    continue;
                }
                if(co>10){
                    break;
                }
                if(entry.getKey().equals("@")){
                    continue;
                }
                System.out.println(co + "-)" + entry.getKey());
                co++;
            }
        }
        else {
            System.out.println("There is no trending topic or mention.");
        }
    }
    public static List<Map.Entry<String,Integer>> sortByValue(Map<String,Integer> map){
        Set<Map.Entry<String,Integer>> set= map.entrySet();
        List<Map.Entry<String,Integer>> list=new ArrayList<>(set);
        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>(){
            public int compare(Map.Entry<String,Integer> x,
                               Map.Entry<String,Integer> y){
                return (y.getValue()).compareTo(x.getValue());
            }
        });
        return list;
    }

}
