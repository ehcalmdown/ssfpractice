package sg.nus.iss.ssfpractice.services;

import java.io.Reader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import sg.nus.iss.ssfpractice.models.CryptoCompare;
import sg.nus.iss.ssfpractice.repositories.CryptoCompareRepo;


@Service
public class CryptoCompareService {
    
    private static final String URL= "https://min-api.cryptocompare.com/data/pricemulti";

    @Value("{$API_KEY}")
    private String key;

    @Autowired
    private CryptoCompareRepo ccRepo;

    public List<CryptoCompare> getPrice(String crypto, String currency){

        CryptoCompare cc = new CryptoCompare();

        //check for caching
        Optional<String> opt = ccRepo.get(crypto, currency);
        String payload;
        System.out.printf(">>> crypto: %s\n", crypto);

        //check for empty input
        if (opt.isEmpty()){
            System.out.println("testing");

            try {
                //url creation using query string
                String url = UriComponentsBuilder.fromUriString(URL)
                        .queryParam("fsym", URLEncoder.encode(crypto, "UTF-8"))
                        .queryParam("tsyms", URLEncoder.encode(currency, "UTF-8"))
                        .queryParam("api_key", key)
                        .toUriString();

    RequestEntity<Void> req = RequestEntity.get(url).build();
    RestTemplate template = new RestTemplate();
    ResponseEntity<String> resp; 
    resp = template.exchange(req, String.class);    
        payload = resp.getBody();
        System.out.println(">>> latest price: " + payload);
        
                        ccRepo.save(crypto, currency, payload);
            } catch (Exception e) {
                //TODO: handle exception
                System.out.println("Error: " + e.getMessage());
                return Collections.emptyList();
            }
        } else {
            payload = opt.get();
            System.out.println(">>>> latest cached: " + payload);
        }
         // Convert payload to JsonObject
        // Convert the String to a Reader
        Reader strReader = new StringReader(payload); 
        JsonReader jsonReader = Json.createReader(strReader);// Create a JsonReader from Reader
        JsonObject jsonObject = jsonReader.readObject();// Read the payload as Json object

       String price = this.
        List<CryptoCompare> list = new LinkedList<>();
        

        cc.setCrypto(crypto);
        cc.setCurrency(currency);
        cc.setPrice(price);
        
        
        list.add(cc);

        return list;
       
    }


}
