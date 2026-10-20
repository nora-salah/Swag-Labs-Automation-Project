package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DataUtils {
    private static final String Test_Data_Path = "src/test/resources/TestData/";

    public static String getJsonData(String jsonFileName, String failed)  {
        try {
            FileReader reader = new FileReader(Test_Data_Path + jsonFileName + ".json");
            JsonElement jsonElement = JsonParser.parseReader(reader);
            return jsonElement.getAsJsonObject().get(failed).getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }  return "";
    }

    public static String getPropertyValue(String fileName, String key) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/test/resources/TestData/" + fileName + ".properties"));
        return properties.getProperty(key);
    }
}
