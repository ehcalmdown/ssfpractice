package sg.nus.iss.ssfpractice.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class CryptoCompare {
    private String crypto;
    private String currency;
    private String price;
 
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getCrypto() {
        return crypto;
    }
    public void setCrypto(String crypto) {
        this.crypto = crypto;
    }

//price will standardize to use cryptoPrice
//convert to string and then back to object
    public static CryptoCompare create(JsonObject jo){ 
        CryptoCompare cc= new CryptoCompare();
        cc.setCrypto(jo.getString("crypto"));
        cc.setCurrency(jo.getString("currency"));
        cc.setPrice(jo.getString("price"));
        return cc;
    }
    public JsonObject toJson(){
        return Json.createObjectBuilder()
        .add("crypto", crypto)
        .add("currency", currency)
        .add("price", price)
        .build();
    }
   
}
