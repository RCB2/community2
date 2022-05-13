package life.maigiang.communtity.dto;

import lombok.Data;

@Data
public class AssessTokenDOT {
    public String client_id;
    public String redirect_uri;
    public String code;
    private String client_secret;
    private String state;
   /* public String response_type;
    public String scope;*/
}
