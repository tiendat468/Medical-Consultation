package com.kltn.medical_consultation.security;

import com.google.common.collect.ImmutableList;
import com.kltn.medical_consultation.domains.TokenStore;
import com.kltn.medical_consultation.enumeration.UserType;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private TokenStore tokenStore;

    @Value("${authentication.ignore:}")
    private String[] ignores;

    @Value("${medicalConsultation.allow.cors:http://localhost:3000,http://localhost:4200,http://localhost:8081}")
    private String[] allowCors;


    @Autowired
    public SecurityConfig(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        HttpSecurity httpSercurity = http.headers().disable()
//
//                .cors().disable()
//                .requestCache().disable()
//                .csrf().disable()
//
//                ;
////
//        BasicAuthenticationFilter filter = new Oauth2AuthorizationFilter(authenticationManager(), tokenStoreService);
//        httpSercurity.addFilterBefore(filter, BasicAuthenticationFilter.class)
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
//
//
//        http.authorizeRequests()
//                .anyRequest().authenticated()
//        ;

        BasicAuthenticationFilter filter = new Oauth2AuthorizationFilter(authenticationManager(), tokenStore);
        HttpSecurity httpSercurity = http.headers().disable()
                .cors()
                .and()
                .requestCache().disable()
                .csrf().disable().authorizeRequests()
                .and()
                //  .addFilter(authenticationFilter)
                //.addFilter(v2AuthenticationFilter)
                ;

        httpSercurity.addFilterBefore(filter, BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());

        httpSercurity
                .authorizeRequests()
                        .antMatchers("/v1/user/**")
                            .hasAnyAuthority(
                                UserType.ADMIN.getCode()
                            )
//                        .antMatchers("v1/feature/**")
//                            .hasAnyAuthority(
//                                UserType.USER.getCode(),
//                                UserType.ADMIN.getCode()
//                            )

        ;
        http.authorizeRequests().anyRequest().authenticated();
    }


    @Override
    public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
        web.ignoring().antMatchers(ignores)
        ;


        //
//        String uriByPassAuthor = tokenSetting.getUriByPassToken();
//        String[] uris = uriByPassAuthor.split(Pattern.quote(","));
//        if (uris.length > 0){
//            for (String uri : uris){
//                config.antMatchers(uri);
//            }
//        }

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        configuration.setAllowedOrigins(ImmutableList.copyOf(allowCors));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    public class CustomAccessDeniedHandler implements AccessDeniedHandler {

        @Override
        public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.setStatus(401);

            JsonObject rs = new JsonObject();
            rs.put("code" , 99);
            rs.put("msg" , "Không có quyền truy cập");
            httpServletResponse.getWriter().write(rs.toString());
        }
    }

    public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {

            res.setContentType("application/json;charset=UTF-8");
            res.setStatus(401);
            res.getWriter().write(AuthorFailResponse.toJson());
        }
    }

}
