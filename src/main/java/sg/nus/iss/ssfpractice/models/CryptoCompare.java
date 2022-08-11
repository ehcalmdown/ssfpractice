package sg.nus.iss.ssfpractice.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class CryptoCompare {

    private String coinName;
    private String currency;
    private String price;
    public String getCoinName() {
        return coinName;
    }
    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }
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


    public static CryptoCompare create(JsonObject jo){
        CryptoCompare cc = new CryptoCompare();
        cc.setCoinName(jo.getString("coinName"));
        cc.setCurrency(jo.getString("currency"));
        cc.setPrice(jo.getString("price"));
        return cc;
    }

    public JsonObject toJson(){
        return Json.createObjectBuilder()
                .add("coinName", coinName)
                .add("currency", currency)
                .add("price", price)
                .build();
    }

   
}
