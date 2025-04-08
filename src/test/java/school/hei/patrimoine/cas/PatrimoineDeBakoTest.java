package school.hei.patrimoine.cas;

import org.junit.jupiter.api.Test;
import school.hei.patrimoine.modele.Argent;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.possession.*;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static school.hei.patrimoine.modele.Devise.MGA;

public class PatrimoineDeBakoTest {

    @Test
    void patrimoine_de_bako_au_31_decembre_2025() {
        
        LocalDate dateInitiale = LocalDate.of(2025, 4, 8);
        LocalDate dateFinale = LocalDate.of(2025, 12, 31);

        // Création des possessions
        Possession bni = new Compte("Compte courant BNI", LocalDate.of(2025, 4, 8), new Argent(2_000_000, Devise.MGA));
        Possession bmoi = new Compte("Compte épargne BMOI", LocalDate.of(2025, 4, 8), new Argent(625_000, Devise.MGA));
        Possession coffre = new Compte("Coffre-fort maison", LocalDate.of(2025, 4, 8), new Argent(1_750_000, Devise.MGA));

        LocalDate dateAcquisition = LocalDate.of(2025, 4, 8);
        Devise devise = Devise.MGA;

        Possession ordinateur = new Materiel(
                "Ordinateur portable",
                dateAcquisition,
                LocalDate.of(2025, 4, 8),
                new Argent(3_000_000, devise),
                12.0 / 100
        );

        Compte compteBNI = new Compte(
                "Compte courant BNI",
                LocalDate.of(2025, 4, 8),
                LocalDate.of(2025, 4, 8),
                new Argent(2_000_000, Devise.MGA)
        );

        Compte compteBMOI = new Compte(
                "Compte courant BMOI",
                LocalDate.of(2025, 4, 8),
                LocalDate.of(2025, 4, 8),
                new Argent(625_000 , Devise.MGA)
        );
        
        // Flux Argent
        Possession salaire = new FluxArgent(
                "Salaire mensuel",
                compteBNI,
                dateInitiale,
                dateFinale,
                2,
                new Argent(2_125_000, Devise.MGA)
        );

        Possession depenseVie = new FluxArgent(
                "Dépenses mensuelles de vie",
                compteBNI,
                dateInitiale,
                dateFinale,
                1,
                new Argent(-700_000, Devise.MGA)
        );

        Possession loyer = new FluxArgent(
                "Loyer colocation",
                compteBNI,
                dateInitiale,
                dateFinale,
                26,
                new Argent(-600_000, Devise.MGA)
        );

        Possession virementEpargne = new TransfertArgent(
                "Virement épargne BMOI",
                compteBNI,
                compteBMOI,
                dateInitiale,
                dateFinale,
                3,
                new Argent(200_000, Devise.MGA)
        );

        // Groupe de possessions
        GroupePossession groupe = new GroupePossession(
                "Patrimoine de Bako",
                Devise.MGA,
                dateFinale,
                Set.of(bni, bmoi, coffre, ordinateur, salaire, depenseVie, loyer, virementEpargne)
        );

        // Calcul du patrimoine de Bako à la date donnée
        var patrimoineDeBakoLe31Decembre2025 = groupe.projectionFuture(LocalDate.of(2025, 12, 31));

        // Vérification de la valeur du patrimoine au 31 décembre 2025
        assertEquals(new Argent(14_440_000, Devise.MGA), patrimoineDeBakoLe31Decembre2025.getValeurComptable(Devise.MGA));
    }
}
