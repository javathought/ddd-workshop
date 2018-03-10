#language: fr
# les nombres doivent respecter les séparateurs français

Fonctionnalité: gestion des comptes bancaires

  Scénario: un compte est créé avec un solde à zéro
    Quand je crée le compte 'A0001' en EUR
    Alors le solde du compte 'A0001' est 0,0 EUR

  Scénario: un dépot incrémente le solde de montant déposé
    Quand je dépose 5,1 EUR sur le compte 'A0001'
    Alors le solde du compte 'A0001' est 5,1 EUR

  Scénario: Une opération dans la mauvaise devise est refusée
    Quand je dépose 5,02 USD sur le compte 'A0001'
    Alors l'opération est refusée

