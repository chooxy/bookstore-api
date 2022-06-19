/* (C)2022 */
package com.example.demo.util;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.json.*;

public class JWT {
    public static String getPayloadFromJwt(String token) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] jwtChunks = token.split("\\.");

        return new String(decoder.decode(jwtChunks[1]));
    }

    public static List<String> getGroupsFromJwt(String token) {
        List<String> groups = new ArrayList<>();
        String payload = getPayloadFromJwt(token);
        JSONObject jsonObject = new JSONObject(payload);
        for (Object groupItem : jsonObject.getJSONArray("cognito:groups")) {
            groups.add(groupItem.toString());
        }

        return groups;
    }
}
