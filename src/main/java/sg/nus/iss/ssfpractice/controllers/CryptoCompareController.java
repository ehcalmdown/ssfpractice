package sg.nus.iss.ssfpractice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.nus.iss.ssfpractice.models.CryptoCompare;
import sg.nus.iss.ssfpractice.services.CryptoCompareService;

@Controller
@RequestMapping("/cryptocompare")
public class CryptoCompareController {
    @Autowired
    private CryptoCompareService ccSvc;

    @GetMapping
    public String getCryptoCompare(Model model, @RequestParam String coinName, String currency){
        List<CryptoCompare> cryptoCompare = ccSvc.getCryptoCompare(coinName, currency);
        model.addAttribute("coinName", coinName.toLowerCase());
        model.addAttribute("cryptoCompare", cryptoCompare);
        return "cryptoCompare";

    }
    
    
}
