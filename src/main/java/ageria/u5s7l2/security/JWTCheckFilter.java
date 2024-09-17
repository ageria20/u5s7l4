package ageria.u5s7l2.security;


import ageria.u5s7l2.entities.Employee;
import ageria.u5s7l2.exception.UnauthorizedException;
import ageria.u5s7l2.services.EmployeeService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTCheckFilter extends OncePerRequestFilter {

    @Autowired
    JWTTools jwtTools;

    @Autowired
    EmployeeService employeeService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1 se nella richiesta c'e' l'header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("INSERT DATA CORRECTLY");
        String accessToken = authHeader.substring(7);
        jwtTools.verifyToken(accessToken);
        String id = this.jwtTools.extractIdFromToken(accessToken);
        Employee employeeFromDb = this.employeeService.findById(Long.valueOf(id));
        Authentication authentication = new UsernamePasswordAuthenticationToken(employeeFromDb, null, employeeFromDb.getAuthorities());
        // aggiungiamo l'employee trovato e autenticato al context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // metodo per disabilitare il filtro su alcune richieste
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
