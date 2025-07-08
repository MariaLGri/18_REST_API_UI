package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GenerateTokenResponseModel {
    @JsonProperty("token")
    private String token;

    @JsonProperty("expires")
    private String expires;

    @JsonProperty("status")
    private String status;

    @JsonProperty("result")
    private String result;
}
