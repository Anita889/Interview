package interviews;

import java.io.FileNotFoundException;
import java.util.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class FundingRaised {

     //init csvData  object
    private static List<String[]> getData() throws IOException, CsvValidationException {
        List<String[]>  csvData = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader("startup_funding.csv"));
        String[] row;

        while ((row = reader.readNext()) != null) {
            csvData.add(row);
        }
        reader.close();
        csvData.remove(0);
        return csvData;
    }
    //at first make map,which statically has key-values for row name and index of row,
    // then remove all others,which not equal our key
    public static List<Map<String, String>> filterRequest(Map<String, String> options) throws IOException, CsvValidationException {
        List<String[]> csvData = getData();

        Map<String,Integer> mapCheck=getSearchingKeyValues();
        List<String> listOfKeys =getAllKeys();

        for(Map.Entry<String, Integer> obj : mapCheck.entrySet()){
            if(options.containsKey(obj.getKey())) {
                csvData.removeIf(csvDatum -> !csvDatum[obj.getValue()].equals(options.get(obj.getKey())));
            }
        }
        List<Map<String, String>> output = new ArrayList<>();
        for (String[] csvDataObj : csvData) {
            Map<String, String> mapped = new HashMap<>();
            for(String list:listOfKeys)
                mapped.put(list,csvDataObj[listOfKeys.indexOf(list)]);
            output.add(mapped);
        }
        return output;
    }

    //get All keys for result
    public static List<String> getAllKeys(){
        return List.of("permalink",
            "company_name",
            "number_employees",
            "category","city",
            "state",
            "funded_date",
            "raised_amount",
            "raised_currency",
            "round");
    }
    //get all keys and values for search
    public static Map<String,Integer> getSearchingKeyValues(){
       return Map.of("company_name",1,
                "city",4,
                "state",5,
                "round",9);
    }

    //this method can return unique member of table or throw exception
    public static Map<String, String> findUniqueMember(Map<String, String> options) throws Exception {
        List<String[]> csvData =getData();

        Map<String, Integer> mappCheck =getSearchingKeyValues();
        List<String> allKeys =getAllKeys();

        if(options.size()==mappCheck.size())
        {
            int index=0;
            for(Map.Entry<String, Integer> obj : mappCheck.entrySet()){
                if(options.containsKey(obj.getKey())) {
                    csvData.removeIf(csvDatum -> !csvDatum[obj.getValue()].equals(options.get(obj.getKey())));
                }
            }
            Map<String, String> mapReturn = new HashMap<>();
            int i=0;
            List<String> listOfStrings = csvData.stream()
                    .flatMap(Arrays::stream)
                    .toList();
                while (i < listOfStrings.size()) {
                    mapReturn.put(allKeys.get(i), listOfStrings.get(i));
                    i++;
                }
                if(mapReturn.size()!=0)
                return mapReturn;
            }
               throw new NoSuchEntryException();
        }
//just check my code
    public static void main(String[] args) throws Exception {
        Map<String,String> map=new HashMap<>();
        map.put("city","Palo Alto");
        map.put("company_name","Facebolkjhgok");
        map.put("state","CA");
        map.put("round","b");
        System.out.println(findUniqueMember(map).toString());
    }
}
//facebook,Facebook,450,web,Palo Alto,CA,1-Apr-06,27500000,USD,b

class NoSuchEntryException extends Exception {}