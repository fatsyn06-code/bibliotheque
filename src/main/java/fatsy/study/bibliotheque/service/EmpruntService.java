package fatsy.study.bibliotheque.service;


import fatsy.study.bibliotheque.model.Emprunt;
import fatsy.study.bibliotheque.model.Livre;
import fatsy.study.bibliotheque.model.Utilisateur;
import fatsy.study.bibliotheque.repository.EmpruntRepository;
import fatsy.study.bibliotheque.repository.LivreRepository;
import fatsy.study.bibliotheque.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class EmpruntService {

    private final EmpruntRepository empruntRepository;
    private final LivreRepository livreRepository;
    private final UtilisateurRepository utilisateurRepository;

    public EmpruntService(EmpruntRepository empruntRepository,
                          LivreRepository livreRepository,
                          UtilisateurRepository utilisateurRepository) {
        this.empruntRepository = empruntRepository;
        this.livreRepository = livreRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Transactional
    public Emprunt emprunter(Long livreId, Long utilisateurId) {
        Livre livre = livreRepository.findById(livreId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livre introuvable"));
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));

        if (!livre.isDisponible()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ce livre est déjà emprunté");
        }

        livre.setDisponible(false);
        livreRepository.save(livre);

        Emprunt emprunt = new Emprunt();
        emprunt.setLivre(livre);
        emprunt.setUtilisateur(utilisateur);
        emprunt.setDateEmprunt(LocalDate.now());
        return empruntRepository.save(emprunt);
    }

    @Transactional
    public Emprunt retourner(Long empruntId) {
        Emprunt emprunt = empruntRepository.findById(empruntId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Emprunt introuvable"));

        if (emprunt.getDateRetour() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ce livre a déjà été retourné");
        }

        emprunt.setDateRetour(LocalDate.now());
        Livre livre = emprunt.getLivre();
        livre.setDisponible(true);
        livreRepository.save(livre);
        return empruntRepository.save(emprunt);
    }
}