package com.service1.controllers;

import com.google.gson.Gson;
import com.service1.dao.CardHitsDAOImpl;
import com.service1.model.CardHits;
import com.service1.pojo.StatResponse;
import com.service1.service.TransactionService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
@CrossOrigin(origins = "*")
@RestController
public class CardsController extends BaseController {

    final static String DEFAULT_CARD_CHECK_URL = "https://lookup.binlist.net/";

    @Autowired
    CardHitsDAOImpl cardsDao;
    @Autowired
    TransactionService service;

    Logger logger = Logger.getLogger("myLogger");
    Gson gson = new Gson();

    /**
     * Returns Card related Stats
     *
     * @param request
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "stats", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getStats(HttpServletRequest request, @RequestParam(value = "limit", required = true) int limit, @RequestParam(value = "offset") int offset, HttpServletResponse httpReaponse) {
        logger.info("Reached");
        Map<String, Integer> payload = new HashMap<>();
        List<CardHits> hits = service.getHits(limit, offset);
        StatResponse obj = new StatResponse();
        obj.setSuccess(true);
        obj.setLimit(limit);
        obj.setStart(offset);
        obj.setSize(hits.size());
        logger.info(Integer.toString(hits.size()));
        for (CardHits hit : hits) {
            logger.info("Card:" + hit.getCardNumber() + " with number of hits " + Integer.toString(hit.getHits()));
            payload.put(hit.getCardNumber(), hit.getHits());
        }
        obj.setPayload(payload);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        headers.add("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
        logger.info(gson.toJson(obj));
        return ResponseEntity.ok()
                .headers(headers)
                .body(gson.toJson(obj));
    }

    @RequestMapping(value = "verify/{cardNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity verifyCard(HttpServletRequest request, @PathVariable(value = "cardNumber") String cardNumber, HttpServletResponse httpReaponse) throws ProtocolException, IOException {

        CardHits hit = service.getByCardNumber(cardNumber);
        httpReaponse.setHeader("Access-Control-Allow-Origin", "*");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        headers.add("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
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
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(response.toString());
        } else {
            System.out.println("GET request not worked");
        }
        return null;
    }
}
