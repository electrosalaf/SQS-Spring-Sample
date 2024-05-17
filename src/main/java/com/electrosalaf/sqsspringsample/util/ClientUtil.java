package com.electrosalaf.sqsspringsample.util;

import com.electrosalaf.sqsspringsample.configuration.LocalDateTimeTypeAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;


import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ibrahim Lawal
 * @createdOn 18/March/2024
 */

@UtilityClass
public class ClientUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Gson gsonMapper = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();;


   private final Pattern WORD_FINDER = Pattern.compile("(([A-Z]?[a-z]+)|([A-Z]))");


    public String getIpAddress(HttpServletRequest request) {
        String ip = Optional.ofNullable(request.getHeader("X-Real-IP")).orElse(request.getRemoteAddr());
        if (ip.equals("0:0:0:0:0:0:0:1")) ip = "127.0.0.1";
        return ip;
    }
    public String getIpAddressFromRequest(HttpServletRequest request) {
        String ip = Optional.ofNullable(request.getHeader("X-Real-IP")).orElse(request.getRemoteAddr());
        if (ip.equals("0:0:0:0:0:0:0:1")) ip = "localhost";
        return ip;
    }



    public ObjectMapper getObjectMapper(){
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public Gson getGsonMapper(){
        return gsonMapper;
    }



    private List<String> findWordsInMixedCase(String text) {
        Matcher matcher = WORD_FINDER.matcher(text);
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group(0));
        }
        return words;

    }

    private String wordCase(String word) {
        return word.substring(0, 1).toUpperCase()
                + word.substring(1).toLowerCase();
    }

    public String capitalizeFirstLetter(String word){
        return word.substring(0, 1).toUpperCase()
                + word.substring(1);
    }

    public String sentenceCase(String text) {
       List<String> words =  findWordsInMixedCase(text);
        List<String> capitalized = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            String currentWord = words.get(i);
            if (i == 0) {
                capitalized.add(wordCase(currentWord));
            } else {
                capitalized.add(currentWord.toLowerCase());
            }
        }
        return String.join(" ", capitalized) + ".";
    }

    public String getFormattedDate() {
        return LocalDateTime.now().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("h:mm:ss a, MMMM d, Y"));

    }

    public String commaSeparatedName(@NonNull String name){
        return name.concat(",");
    }

    public String getReceiptDate() {
        return LocalDateTime.now().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("MMM d, Y | h:mm a"));
    }

    public String getReceiptDate(LocalDateTime localDateTime) {
        return localDateTime.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("MMM d, Y | h:mm a"));
    }


    public  String encodeAccountNumber(@Nullable String input) {
        if(input == null) return "00*****000";
        int length = input.length();
        if (length <= 6) {
            return input;
        } else {
            return input.substring(0, 2) + "*****" + input.substring(length - 3, length);
        }
    }



    public boolean validatePhoneNumber(String phoneNumber) {
        String regex = "^\\+(?:[0-9] ?){6,14}[0-9]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }





}

