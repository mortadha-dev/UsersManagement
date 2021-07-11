package mortadha.formation.UsersManagement.utilities;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import mortadha.formation.UsersManagement.constants.SecurityConstant;
import static java.util.Arrays.stream;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static mortadha.formation.UsersManagement.constants.SecurityConstant.* ;
import mortadha.formation.UsersManagement.model.UserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTTokenProvider {
    @Value("${jwt.secret}")
    private String secret;

    //this method generate the token
    public String generateJwtToken(UserPrincipal userPrincipal){
        String[] permissions = getPermessionsFromUser(userPrincipal);
        return JWT.create().withIssuer(GET_ARRAYS_LLC).withAudience(GET_ARRAYS_ADMINISTRATION)
                .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername())
                .withArrayClaim(AUTHORITIES, permissions).withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(secret.getBytes()));

    }

    public List<GrantedAuthority> getAuthorities(String token){
        String[] permissions =  getPermessionsFromToken(token);
        return stream(permissions).map(SimpleGrantedAuthority:: new ).collect(Collectors.toList());
    }
    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities , HttpServletRequest request){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                UsernamePasswordAuthenticationToken(username,null, authorities) ;
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthenticationToken;
    }

    public boolean isTokenValid(String username, String token){
    JWTVerifier verifier = getJWTVerifier();
    return StringUtils.isNotEmpty(username) && !isTokenExpired(verifier, token) ;

    }

public String getSubject(String token){
    JWTVerifier verifier = getJWTVerifier();
    return verifier.verify(token).getSubject();

}


    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date()) ;
    }


    private String[] getPermessionsFromToken(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class) ;
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier ;
        try{
            Algorithm algorithm = HMAC512(secret);
            verifier= JWT.require(algorithm).withIssuer(GET_ARRAYS_LLC).build();
        }
        catch (JWTVerificationException ex){
          throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return  verifier ;

    }

    private String[] getPermessionsFromUser(UserPrincipal userPrincipal) {
        List<String>authorities= new ArrayList<>();
        for(GrantedAuthority grantedAuthority : userPrincipal.getAuthorities()){
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities.toArray(new String[0]);
    }

}
