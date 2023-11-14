package interviews;

import java.io.FileNotFoundException;
import java.util.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

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
    public static Map<String, String> findUniqueMember(Map<String, String> options) throws IOException, NoSuchEntryException, CsvValidationException {
        List<String[]> csvData =getData();

        Map<String, Integer> mappCheck =getSearchingKeyValues();
        List<String> allKeys =getAllKeys();

        if(options.size()!=mappCheck.size())
        throw new NoSuchEntryException();
        else{
            for(Map.Entry<String, Integer> obj : mappCheck.entrySet()){
                if(options.containsKey(obj.getKey())) {
                    csvData.removeIf(csvDatum -> !csvDatum[obj.getValue()].equals(options.get(obj.getKey())));
                }
            }
            Map<String, String> mapReturn = new HashMap<>();
            int i=0;
            while( i<csvData.get(0).length){
                mapReturn.put(allKeys.get(i),csvData.get(0)[i]);
                i++;
            }

            return mapReturn;
        }
    }
//just check my code
    public static void main(String[] args) throws CsvValidationException, IOException, NoSuchEntryException {
        Map<String,String> map=new HashMap<>();
        map.put("city","Tempe");
        map.put("company_name","LifeLock");
        map.put("state","AZ");
        map.put("round","b");
        System.out.println(findUniqueMember(map).toString());
    }
}

class NoSuchEntryException extends Exception {}