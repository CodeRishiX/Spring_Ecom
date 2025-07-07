//package codex_rishi.ecom_spring.config;
//
//import codex_rishi.ecom_spring.service.CustomOAuth2UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.context.annotation.Bean;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private CustomOAuth2UserService customOAuth2UserService;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/",                         // root
//                                "/index.html",               // homepage
//                                "/css/**",                   // styles
//                                "/js/**",                    // JS scripts
//                                "/images/**",                // images
//                                "/api/products",
//                                "/api/products/search",
//                                "/api/product/*/image",
//                                "/api/New-Arrival",
//                                "/api/productdetails/*"
//                        ).permitAll()
//                        .anyRequest().authenticated()
//                )
//                .oauth2Login(oauth -> oauth
//                        .userInfoEndpoint(userInfo -> userInfo
//                                .userService(customOAuth2UserService)
//                        )
//                        .defaultSuccessUrl("/index.html", true) // successful login redirects here
//                );
//
//        return http.build();
//    }
//}
package codex_rishi.ecom_spring.config;


import codex_rishi.ecom_spring.service.CustomOAuth2SuccessHandler;
import codex_rishi.ecom_spring.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private CustomOAuth2SuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Allow all static resources and public APIs
                        .requestMatchers(
                                "/", "/index.html", "/css/**", "/js/**", "/images/**",
                                "/about.html", "/new-arrivals.html", "/api/products",
                                "/api/products/search", "/api/product/*/image",
                                "/api/New-Arrival", "/api/productdetails/*",
                                "/api/auth/status", "/add-product.html",
                                "/update-product.html", "/product-details.html"
                        ).permitAll()
                        // Allow authentication endpoint
                        .requestMatchers("/api/user/role").permitAll()
                        // Allow product operations temporarily
                        .requestMatchers("/api/addproduct", "/api/product/**",
                                       "/api/productupdate/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(successHandler)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

}
