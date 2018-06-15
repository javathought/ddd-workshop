#language: fr
Fonctionnalité: Création de comptes bancaires et réception de transfert
  En tant que client je veux pouvoir recevoir des transferts d'argent sur mon compte

  Contexte: un compte est créé avec un solde à zéro
    Quand je crée le compte 'T0001' en EUR
    Et le découvert autorisé du compte 'T0001' est de 150,0
    Et je dépose 1000,00 EUR sur le compte 'T0001'
    Alors le solde du compte 'T0001' est 1000,0 EUR

  Scénario: un transfert est reçu et exécuté par la banque
    Quand je reçois de la banque 'BICEFRPPXXX' un transfert de 1300,00 EUR pour le compte 'T0001'
    Alors l'opération est acceptée
    Et l'opération est au statut exécuté
    Et le solde du compte 'T0001' est 2300,00 EUR
    Quand je consulte le compte 'T0001'
    Et la taille de l'historique est 2
    Et la dernière opération est de 1300,0 EUR

  Scénario: un transfert est reçu pour un compte inexistant
    Quand je reçois de la banque 'BICEFRPPXXX' un transfert de 1300,00 EUR pour le compte 'I0001'
    Alors un rejet de l'opération est renvoyé à la banque émettrice 'BICEFRPPXXX'


