package fatsy.study.bibliotheque.controller;

import fatsy.study.bibliotheque.model.Emprunt;
import fatsy.study.bibliotheque.repository.EmpruntRepository;
import fatsy.study.bibliotheque.service.EmpruntService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprunts")
public class EmpruntController {

    private final EmpruntService empruntService;
    private final EmpruntRepository empruntRepository;

    public EmpruntController(EmpruntService empruntService, EmpruntRepository empruntRepository) {
        this.empruntService = empruntService;
        this.empruntRepository = empruntRepository;
    }

    public record EmpruntRequest(@NotNull Long livreId, @NotNull Long utilisateurId) {}

    @GetMapping
    public List<Emprunt> lister() {
        return empruntRepository.findAll();
    }

    @PostMapping
    public Emprunt emprunter(@Valid @RequestBody EmpruntRequest requete) {
        return empruntService.emprunter(requete.livreId(), requete.utilisateurId());
    }

    @PostMapping("/{id}/retour")
    public Emprunt retourner(@PathVariable Long id) {
        return empruntService.retourner(id);
    }
}
