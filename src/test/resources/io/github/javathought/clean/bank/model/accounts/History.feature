#language: fr

  Fonctionnalité: Historiques des opérations
    En tant que client, je veux pouvoir consulter les opérations du compte en commençant par les plus récentes
    afin de pouvoir vérifier les opérations effectuées sur le compte

  Scénario: Consultation de l'historique du compte A0003
    Quand je crée le compte 'A0003' en EUR
    Et je dépose 5,43 EUR sur le compte 'A0003'
    Et je dépose 1396,32 EUR sur le compte 'A0003'
    Et je dépose 1100,00 EUR sur le compte 'A0003'
    Et je consulte le compte 'A0003'
    Alors la taille de l'historique est 3
    Et la dernière opération est de 1100,0 EUR
    Et l'opération précédente est de 1396,32 EUR
    Et l'opération précédente est de 5,43 EUR

