package project.config.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetails;

    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetails) {
        this.jwtUtil = jwtUtil;
        this.userDetails = userDetails;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
       String userHeader=request.getHeader("Authorization");
       if(userHeader !=null && userHeader.startsWith("Bearer ")){
         String jwt =userHeader.substring(7);
         if(userHeader.isBlank()){
              response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid JWT Token in Bearer Header");
         }
         else{
             try {
                 String claim = jwtUtil.validateTokenAndRetrieveClaim(jwt);
                 UserDetails details = userDetails.loadUserByUsername(claim);
                 UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(details,
                            details.getPassword(),
                            details.getAuthorities());
                 if(SecurityContextHolder.getContext().getAuthentication() ==null){
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                 }

             } catch (JWTVerificationException exception){
                  response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid JWT Token");
             }
         }
         filterChain.doFilter(request,response);
       }

    }
}
