package com.berzellius.integrations.comagicru.deserializer;

import com.berzellius.integrations.comagicru.dto.visitorinfo.Ac;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by berz on 26.03.2017.
 */
public class ComagicVisitorInfoAcsDeserializer extends JsonDeserializer<ArrayList<Ac>> {
    @Override
    public ArrayList<Ac> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode node = objectCodec.readTree(jsonParser);
        String text = node.asText();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(text);

        if(jsonNode.isContainerNode()) {
            ArrayList<Ac> acs = new ArrayList<>();

            Iterator<JsonNode> nodeIterator = jsonNode.iterator();
            while (nodeIterator.hasNext()) {
                JsonNode n = nodeIterator.next();

                Ac ac = new Ac();

                JsonNode name = n.findValue("name");
                if(name != null)
                    ac.setName(name.asText());

                JsonNode id = n.findValue("id");
                if(id != null)
                    ac.setId(id.asLong());

                JsonNode is_removed = n.findValue("is_removed");
                if(is_removed != null)
                    ac.setIs_removed(n.findValue("is_removed").asBoolean());

                JsonNode state = n.findValue("state");
                if(state != null)
                    ac.setState(state.asText());

                acs.add(ac);
            }

            return acs;
        }
        else return null;
    }
}
