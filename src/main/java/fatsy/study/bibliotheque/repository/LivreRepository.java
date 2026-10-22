package fatsy.study.bibliotheque.repository;

import fatsy.study.bibliotheque.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivreRepository extends JpaRepository<Livre, Long> {
}