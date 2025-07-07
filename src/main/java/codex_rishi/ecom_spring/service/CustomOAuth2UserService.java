package codex_rishi.ecom_spring.service;


import codex_rishi.ecom_spring.model.Role;
import codex_rishi.ecom_spring.model.User;
import codex_rishi.ecom_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    private final Set<String> adminEmails = Set.of(
            "debangshubhattacharya4@gmail.com"
    );

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");

        Role role = adminEmails.contains(email) ? Role.ADMIN : Role.USER;

        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = User.builder()
                    .email(email)
                    .name(name)
                    .imageUrl(picture)
                    .role(role)
                    .build();
        } else {
            user.setName(name); // update info
            user.setImageUrl(picture);
            user.setRole(role); // update role based on email
        }

        userRepository.save(user);

        return oAuth2User;
    }
}
