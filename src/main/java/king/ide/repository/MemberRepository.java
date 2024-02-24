package king.ide.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import king.ide.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Optional<Member> findByLoginId(String loginId) {
        List<Member> findMember = em.createQuery("select m from Member m where m.loginId = :loginId", Member.class)
                .setParameter("loginId", loginId)
                .getResultList();
        return findMember.stream().findAny();
    }

    public Member findById(Long id) {
        return em.find(Member.class, id);
    }
}
