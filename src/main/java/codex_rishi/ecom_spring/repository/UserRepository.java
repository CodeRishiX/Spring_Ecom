package codex_rishi.ecom_spring.repository;



import codex_rishi.ecom_spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
