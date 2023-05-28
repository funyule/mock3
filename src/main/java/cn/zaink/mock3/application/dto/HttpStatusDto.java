package cn.zaink.mock3.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zaink
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class HttpStatusDto {

    private String name;

    private String reasonPhrase;
    private int value;
}
