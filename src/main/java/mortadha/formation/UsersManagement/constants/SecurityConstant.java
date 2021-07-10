package mortadha.formation.UsersManagement.constants;

import lombok.Data;

@Data
public class SecurityConstant {
    public static final long EXPIRATION_TIME = 432_000_000;  //5 days expressed in milliseconds
    public static final String TOKEN_Header = "Bearer ";
    public static final String JWT_Token_Header = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED="Token cannot be verified" ;
    public static final String GET_ARRAYS_LLC= "Mortadha , LLC";
    public static final String GET_ARRAYS_ADMINISTRATION="users management";
    public static final String AUTHORITIES ="Authorities";
    public static final String FORBIDDEN_MESSAGE="you need to login in order to access this page";
    public static final String ACCESS_DENIED_MESSAGE="you don't have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD="OPTIONS";
    public static final String[] PUBLIC_URLS = {"/user/login", "/user/register", "/user/resetpassword/**","/user/image/**"};



}