package mortadha.formation.UsersManagement.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data

public class HttpResponse {
    private int httpStatusCode; //200,500,400
    private HttpStatus httpStatus ;
    private String reason ;
    private String message ;//developer message


}
