package king.ide.service;

import king.ide.domain.Member;
import king.ide.dto.CustomUserDetails;
import king.ide.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByLoginId(loginId).orElse(null);

        if (findMember != null) {
            return new CustomUserDetails(findMember);
        }

        return null;
    }
}
