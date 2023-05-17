package com.robosoftin.lorem_food_app.security;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.robosoftin.lorem_food_app.exception.AuthErrorResponse;
import com.robosoftin.lorem_food_app.exception.AuthExceptionHandler;
import com.robosoftin.lorem_food_app.exception.BearerTokenNotFoundException;
import com.robosoftin.lorem_food_app.exception.UnauthorizedException;
import com.robosoftin.lorem_food_app.service.BlacklistTokenService;
import com.robosoftin.lorem_food_app.service.JwtService;
import com.robosoftin.lorem_food_app.utility.JwtUtility;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.http.HttpRequest;
import org.apache.http.ProtocolException;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.Map;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private BlacklistTokenService blacklistTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String authorization = request.getHeader("Authorization");
        String token = null;
        String username = null;
        AuthExceptionHandler exceptionHandler = new AuthExceptionHandler();
        RequestWrapper wrappedRequest = new RequestWrapper(request);
        try {
            if (authorization != null && authorization.startsWith("Bearer ")) {
                token = authorization.substring(7);
                username = jwtUtility.getUsernameFromToken(token);
                Map<String, Object> jsonRequest = new ObjectMapper().readValue(wrappedRequest.getInputStream(), Map.class);
                if (!jsonRequest.get("emailId").toString().equals(username))
                    throw new UnauthorizedException("Invalid token!");
            } else
                throw new BearerTokenNotFoundException("Couldn't find bearer token");
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = jwtService.loadUserByUsername(username);
                if (!blacklistTokenService.isBlacklisted(token) && jwtUtility.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else
                    throw new UnauthorizedException("Invalid Token");
                filterChain.doFilter(wrappedRequest, response);
            }
        } catch (UnauthorizedException exception) {
            exceptionHandler.handleFilterException(response, HttpStatus.UNAUTHORIZED, exception.getMessage());
        } catch (ExpiredJwtException exception) {
            exceptionHandler.handleFilterException(response, HttpStatus.UNAUTHORIZED, "Token Expired");
        } catch (BearerTokenNotFoundException exception) {
            exceptionHandler.handleFilterException(response, HttpStatus.FORBIDDEN, exception.getMessage());
        }
        catch (Exception exception)
        {
            exceptionHandler.handleFilterException(response,HttpStatus.BAD_REQUEST,exception.getMessage());
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.startsWith("/api/user/") || path.startsWith("/api/restaurant/");
    }

    private static class RequestWrapper extends HttpServletRequestWrapper {
        private final String body;

        public RequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = null;

            try {
                InputStream inputStream = request.getInputStream();
                if (inputStream != null) {
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    char[] charBuffer = new char[128];
                    int bytesRead;
                    while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
                        stringBuilder.append(charBuffer, 0, bytesRead);
                    }
                } else {
                    stringBuilder.append("");
                }
            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }

            body = stringBuilder.toString();
        }

        public String getRequestBody() {
            return body;
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
        }
    }
}
