package fatsy.study.bibliotheque.repository;

import fatsy.study.bibliotheque.model.Emprunt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {
}