#language: fr
# les nombres doivent respecter les séparateurs français

Fonctionnalité: Création de comptes bancaires et opérations de transfert
  En tant que client je veux pouvoir transférer de l'argent vers un compte ouvert dans une autre banque

  Contexte: un compte est créé avec un solde à zéro
    Quand je crée le compte 'T0001' en EUR
    Et le découvert autorisé du compte 'T0001' est de 150,0
    Et je dépose 1100,00 EUR sur le compte 'T0001'
    Et je crée le compte 'C0001' en EUR
    Alors le solde du compte 'T0001' est 1100,0 EUR
    Et le solde du compte 'C0001' est 0,0 EUR

  Scénario: un transfert faisant dépasser le découvert autorisé est interdit
    Quand je transfère 1200,00 EUR du compte 'T0001' vers le compte 'C0001'
    Alors l'opération est refusée

  Scénario: un transfert vers un compte est en attente de réponse de la banque destinatrice
    Quand je transfère 100,00 EUR du compte 'T0001' vers le compte 'C0001'
    Alors l'opération est en attente
    Alors l'opération est annulée

  Scénario: un transfert vers un compte inexistant est annulé
    Quand je transfère 100,00 EUR du compte 'T0001' vers le compte 'I0001'
    Et l'opération est en attente
    Et la banque destinatrice renvoie la réponse négative 'Compte inexistant'
    Alors l'opération est annulée

  Scénario: un transfert vers un compte existant est exécuté
    Quand je transfère 100,00 EUR du compte 'T0001' vers le compte 'C0001'
    Et l'opération est en attente
    Et la banque destinatrice renvoie la réponse 'Opération exécutée'
    Alors l'opération est acceptée
    Et le solde du compte 'T0001' est 1000,00 EUR
