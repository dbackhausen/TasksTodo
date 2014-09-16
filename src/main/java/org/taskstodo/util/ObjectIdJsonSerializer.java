package org.taskstodo.util;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class ObjectIdJsonSerializer extends JsonSerializer<ObjectId> {
  @Override
  public void serialize(ObjectId o, JsonGenerator j, SerializerProvider s) throws IOException, JsonProcessingException {
      if(o == null) {
          j.writeNull();
      } else {
          j.writeString(o.toString());
      }
  }
}
