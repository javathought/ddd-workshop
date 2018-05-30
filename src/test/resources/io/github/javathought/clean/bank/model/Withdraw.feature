#language: fr
# les nombres doivent respecter les séparateurs français

Fonctionnalité: Création de comptes bancaires et opérations de retrait
  En tant que client je veux pouvoir ouvrir un compte
  afin d'y retirer l'argent que j'ai déposé

  Contexte: un compte est créé avec un solde à zéro
    Quand je crée le compte 'D0001' en EUR
    Et je dépose 1100,00 EUR sur le compte 'D0001'
    Alors l'opération est acceptée
    Et l'opération est au statut exécuté
    Et le solde du compte 'D0001' est 1100,0 EUR

  Scénario: un retrait décrémente le solde de montant déposé
    Quand je retire 9,5 EUR sur le compte 'D0001'
    Alors l'opération est acceptée
    Et l'opération est au statut exécuté
    Et le solde du compte 'D0001' est 1090,5 EUR

  Scénario: un retrait faisant dépasser le découvert autorisé est interdit
    Quand je retire 1200,00 EUR sur le compte 'D0001'
    Alors l'opération est refusée

  Scénario: un retrait ne faisant pas dépasser le découvert autorisé est accepté
    Quand le découvert autorisé du compte 'D0001' est de 150,0
    Et je retire 1200,00 EUR sur le compte 'D0001'
    Alors l'opération est acceptée
    Et l'opération est au statut exécuté
    Et le solde du compte 'D0001' est -100,0 EUR

  Scénario: plusieurs retraits décrémentent le solde des montants déposés
    Quand le découvert autorisé du compte 'D0001' est de 150,0
    Et je retire 1100,00 EUR sur le compte 'D0001'
    Alors l'opération est acceptée
    Et l'opération est au statut exécuté
    Et je retire 150,00 EUR sur le compte 'D0001'
    Alors l'opération est acceptée
    Et l'opération est au statut exécuté
    Et le solde du compte 'D0001' est -150,0 EUR

  Scénario: plusieurs retraits décrémentent le solde des montants déposés sans dépasser le découvert
    Quand le découvert autorisé du compte 'D0001' est de 150,0
    Et je retire 1200,00 EUR sur le compte 'D0001'
    Alors l'opération est acceptée
    Et l'opération est au statut exécuté
    Et je retire 151,00 EUR sur le compte 'D0001'
    Alors l'opération est refusée
    Et le solde du compte 'D0001' est -100,0 EUR

  Scénario: Un retrait est dans la mauvaise devise est refusée
    Quand je retire 5,02 USD sur le compte 'D0001'
    Alors l'opération est refusée
