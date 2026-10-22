package fatsy.study.bibliotheque.controller;

import fatsy.study.bibliotheque.model.Livre;
import fatsy.study.bibliotheque.repository.LivreRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livres")
public class LivreController {

    private final LivreRepository livreRepository;

    public LivreController(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    @GetMapping
    public List<Livre> lister() {
        return livreRepository.findAll();
    }

    @PostMapping
    public Livre ajouter(@RequestBody Livre livre) {
        return livreRepository.save(livre);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livre> trouver(@PathVariable Long id) {
        return livreRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livre> modifier(@PathVariable Long id, @RequestBody Livre donnees) {
        return livreRepository.findById(id)
                .map(livre -> {
                    livre.setTitre(donnees.getTitre());
                    livre.setAuteur(donnees.getAuteur());
                    livre.setIsbn(donnees.getIsbn());
                    return ResponseEntity.ok(livreRepository.save(livre));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        if (!livreRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        livreRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}