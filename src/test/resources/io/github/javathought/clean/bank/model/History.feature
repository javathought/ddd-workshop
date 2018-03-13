#language: fr

  Fonctionnalité: Historiques des opérations
    En tant qu'utilisateur, je veux pouvoir consulter l'historiques du compte afin de
    pouvoir vérifier les opérations effectuées sur le compte

  Scénario: Consultation du compte A0003
    Quand je crée le compte 'A0003' en EUR
    Et je dépose 5,43 EUR sur le compte 'A0003'
    Et je dépose 1396,32 EUR sur le compte 'A0003'
    Et je dépose 1100,00 EUR sur le compte 'A0003'
    Et je consulte le compte 'A0003'
    Alors la taille de l'historique est 2
    Et la dernière opération est de 1100 EUR
    Et l'opération précédente est de 1396,32 EUR
    Et l'opération précédente est de 5,43 EUR

