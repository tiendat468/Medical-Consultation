package com.kltn.medical_consultation.security;

import com.kltn.medical_consultation.domains.TokenStore;
import com.kltn.medical_consultation.entities.redis.TokenDTO;
import com.kltn.medical_consultation.models.ShareConstant;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Optional;


public class Oauth2AuthorizationFilter
        extends BasicAuthenticationFilter {

    private TokenStore tokenStore;


    public Oauth2AuthorizationFilter(AuthenticationManager authenticationManager,
                                     TokenStore tokenStore
    ) {
        super(authenticationManager);
        this.tokenStore = tokenStore;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String token = request.getHeader(ShareConstant.HEADER.HEADER_TOKEN);

            if (StringUtils.isEmpty(token)) {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());

                JsonObject rs = new JsonObject();
                rs.put("code", 99);
                rs.put("message", "Vui lòng login để sử dụng CMS");
                response.getWriter().write(rs.toString());
                return;
            }

            token = token.replaceFirst("(?i)" + ShareConstant.HEADER.TOKEN_PREFIX, "");

            Optional<TokenDTO> tokenOpt = tokenStore.getInfoByToken(token);

            if (!tokenOpt.isPresent()) {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());

                JsonObject rs = new JsonObject();
                rs.put("code", 99);
                rs.put("message", "Vui lòng login để sử dụng CMS");
                response.getWriter().write(rs.toString());
                return;
            }

            TokenDTO tokenDTO = tokenOpt.get();
            CustomUserDetail authentication = new CustomUserDetail(
                    tokenDTO,
                    null,
                    AuthorityUtils.createAuthorityList(tokenDTO.getRoleCode())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);


        } catch (AccessDeniedException ex) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);

            JsonObject rs = new JsonObject();
            rs.put("code", 99);
            rs.put("msg", "Không có quyền truy cập");
            response.getWriter().write(rs.toString());
            return;
        }

    }


}
