package com.service1.controllers;

import com.google.gson.Gson;
import com.service1.dao.CardHitsDAOImpl;
import com.service1.model.CardHits;
import com.service1.service.TransactionService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ravindra
 * @since 2020-10-17
 */
@CrossOrigin
@RestController
public class CardsController extends BaseController {

    final static String DEFAULT_CARD_CHECK_URL = "https://lookup.binlist.net/";

    @Autowired
    CardHitsDAOImpl cardsDao;
    @Autowired
    TransactionService service;

    Logger logger = Logger.getLogger("myLogger");
    Gson gson = new Gson();

    @RequestMapping(value = "stats", method = RequestMethod.GET)
    @ResponseBody
    public String getStats(HttpServletRequest request, @RequestParam(value = "limit", required = true) int limit, @RequestParam(value = "offset") int offset) {
        logger.info("Reached");
        List<CardHits> hits = null;
        try {
            hits = service.getHits(limit, offset);
            for (CardHits hit : hits) {
                logger.info("Card:" + hit.getCardNumber() + " with number of hits " + Integer.toString(hit.getHits()));
            }
            //int id = service;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return gson.toJson(hits);
    }

    @RequestMapping(value = "verify/{cardNumber}", method = RequestMethod.GET)
    @ResponseBody
    public String verifyCard(HttpServletRequest request, @PathVariable(value = "cardNumber") String cardNumber) throws ProtocolException, IOException {

        CardHits hit = service.getByCardNumber(cardNumber);
        if (hit == null) {
            service.addCard(cardNumber);
        } else {
            hit.setHits(hit.getHits() + 1);
            service.updateCard(hit);
        }

        URL obj = new URL(DEFAULT_CARD_CHECK_URL + cardNumber);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        logger.info("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
            return response.toString();
        } else {
            System.out.println("GET request not worked");
        }
        return null;
    }
}
