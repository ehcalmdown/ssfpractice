package sg.nus.iss.ssfpractice.services;

import java.io.Reader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
import sg.nus.iss.ssfpractice.models.CryptoCompare;
import sg.nus.iss.ssfpractice.repositories.CryptoCompareRepo;


@Service
public class CryptoCompareService {
    
   private static final String URL = "https://min-api.cryptocompare.com/data/pricemulti";

   @Value("${API_KEY}") //f14b75d8bc0503e27d1a0cc56627523e42b3955bbbbbb91c6b0f73bb1be816ab  API KEY, PASSWORD IS chicken
    private String key;

    @Autowired
    private CryptoCompareRepo ccRepo;

    public List<CryptoCompare> getCryptoCompare(String coinName, String currency){
        CryptoCompare cc = new CryptoCompare();
        Optional<String>opt = ccRepo.get(coinName, currency);
        String payload;
        System.out.printf(">>> coinName: %s\n", coinName.toLowerCase());
        System.out.printf(">>> currency: %s\n", currency.toLowerCase());

        //check for empty case
        if(opt.isEmpty()){
            System.out.println("testing....");
            System.out.println("Obtaining info from Crypto compare...");

            try {
                //create url query string
                String url = UriComponentsBuilder.fromUriString(URL)
                        .queryParam("fsym", URLEncoder.encode(coinName, "UTF-8"))
                        .queryParam("tsyms", URLEncoder.encode(currency, "UTF-8"))
                        .queryParam("api_key", key)
                        .toUriString();
                        RequestEntity<Void> req = RequestEntity.get(url).build(); // Create the GET request, GET url

                RestTemplate template = new RestTemplate(); // Make the call to cryptocompare
                ResponseEntity<String> resp;

                resp = template.exchange(req, String.class); // Throws an exception if status code not in between 200 - 399

                payload = resp.getBody(); // Get the payload and do something with it, redundant payload is like me, a liability
                System.out.println(">>> latest price: " + payload);

                ccRepo.save(coinName, currency, payload);
            } catch (Exception e) {
                //TODO: handle exception
                System.out.println("Error: " + e.getMessage());
                return Collections.emptyList();
            }

        }else{
            //retrieve value for case
            payload = opt.get();
            System.out.printf(">>>>latest cached price= %s\n", payload);
        }
        // Convert payload to JsonObject
        // Convert the String to a Reader
        Reader strReader = new StringReader(payload);
        JsonReader jsonReader = Json.createReader(strReader);
        JsonObject jsonObject = jsonReader.readObject();
        // JsonObject cryptoCompareResult = jsonReader.readObject();
        // JsonArray coinNames = cryptoCompareResult.getJsonArray("cryptoCompare"); //just testing stuff out here
        String price = jsonObject.getString(currency);
        List<CryptoCompare> list = new LinkedList<>();
        cc.setCoinName(coinName);
        cc.setCurrency(currency);
        cc.setPrice(price);

        return list;
    }



}
