package in.kvapps.shwish_wish.webfilter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomCorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Allow OPTIONS request for specific domains
        String originHeader = request.getHeader("Origin");
        if ("OPTIONS".equalsIgnoreCase(request.getMethod()) &&
                (originHeader != null && (originHeader.contains("kvapps.in")))) {

            // Set CORS headers
            response.setHeader("Access-Control-Allow-Origin", originHeader);
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
