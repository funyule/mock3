package cn.zaink.mock3.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * @author zaink
 **/
public class HttpStatusJsonSerializer extends JsonSerializer<HttpStatus> {

    @Override
    public void serialize(HttpStatus value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (null != value) {
            gen.writeNumber(value.value());
            gen.writeStringField("httpStatusReasonPhrase", value.getReasonPhrase());
        }
    }
}
