# Météo
Projet de fin de module en Dev Mobile.
Ce projet a été réalisé en Java sous Android Studio 4.1.1 par Alexandre Vernet et Valentin Arpin

![icone](https://user-images.githubusercontent.com/72151831/104051425-63967780-51e8-11eb-9e6e-8582d3ead120.png)



## Description
Application mobile récupérant des informations météorologique sur l'emplacement déterminé ou sur une ville recherchée. 

## Maquette

![maquette](https://user-images.githubusercontent.com/72151831/107120007-4b6b5400-688b-11eb-88db-ef80425bfd3f.png)



## Pré-requis
L'application a besoin d'une connexion internet pour fonctionner. Si ce n'est pas le cas, une boîte de texte apparaîtra en bas de l'écran pour rediriger dans les paramètres réseaux.

![pas_internet](https://user-images.githubusercontent.com/72151831/104051027-c3d8e980-51e7-11eb-9d8a-d34d50fc6c78.jpg)


Ne fonctionne pas sur émulateur


## 1er lancement
A l'ouverture, l'application va demander la permission LOCALISATION.
Si l'utilisateur refuse, l'application va automatiquement récupérer la météo de Paris et suggérer d'autoriser la localisation pour le bon fonctionnement de l'application.

![permission](https://user-images.githubusercontent.com/72151831/104051128-ecf97a00-51e7-11eb-9107-7d80dc9344a8.jpg)



## Fonctionnement 
L'application va vérifier que l'appareil est bien connecté à internet. Si c'est le cas, l'application va déterminer la position de l'utilisateur et afficher la météo de la ville dans laquelle il se situe. L'application récupère le nom de la ville, la température, les températures minimum et maximum de la journée, le taux d'humidité, la vitesse du vent ainsi que l'heure de lever et de coucher du soleil. 

![accueil](https://user-images.githubusercontent.com/72151831/104054704-dd7d2f80-51ed-11eb-921f-cc358eff54c3.jpg)



## Graphiques
Nous avons utilisé la librairie AndroidChart pour créer un graphique de prévision pour la semaine permettant de prévoir les températures de la semaine en cours.

![graphique](https://user-images.githubusercontent.com/72151831/104054580-a73fb000-51ed-11eb-8f2b-60d2b9bd3130.jpg)



## Menu
Un bouton est présent en bas à droite de l'écran pour afficher un menu. 

![menu](https://user-images.githubusercontent.com/72151831/104052188-9e4cdf80-51e9-11eb-84ba-8f18394de4df.jpg)



Ce menu possède 3 options : 
- Récupérer la météo de la ville dans laquelle l'appareil se situe
- Saisir une ville pour récupérer la météo de n'importe quelle ville de France (exemple : Paris)
- Paramètres pour modifier les unités de températures (°C ou °F) ainsi que la vitesse du vent (km / h, mph, noeuds etc). Ces données seront stockées  dans la mémoire du téléphone et réutilisée à chaque ouverture de l'application et jusqu'aux prochains changements.Ce menu inclut également la version de l'application ainsi que les noms des développeurs.

![preference](https://user-images.githubusercontent.com/72151831/104051160-fbe02c80-51e7-11eb-9180-b7768efde4a8.jpg)
![preference_changer_unite](https://user-images.githubusercontent.com/72151831/104051166-fd115980-51e7-11eb-946b-ce6d6ef73531.jpg)



## Raccourcis
Des raccourcis sur l'icone de l'application sont présents pour démarrer directement l'activité "Saisir une ville" ou "Paramètres".

![raccourcis](https://user-images.githubusercontent.com/72151831/104051071-d6532300-51e7-11eb-9b4c-c93d8f0c85ab.jpg)



## Widget
L'app possède son propre widget affichant la température et soit : 
- Le nom de la ville dans laquelle l'appareil a été localisé
- La ville saisie

Au clic du widget, l'application se lance.

![widget](https://user-images.githubusercontent.com/72151831/104051113-e408a880-51e7-11eb-9116-96978f6c0429.jpg)



## Langues
L'application possède plusieurs fichiers de traductions : 
- Français
- Espagnol
- Italien
- Allemand

L'application est capable de reconnaître la langue utilisée par l'appareil. Si ce dernier est configuré dans une des langues citées au dessus, elle traduira chaque texte dans la traduction appropriée, sinon, elle choisira l'anglais comme langue par défaut.

![langue_allemand](https://user-images.githubusercontent.com/72151831/104055061-67c59380-51ee-11eb-9dc4-9ddb964e4e21.jpg)
![langue_espagnol](https://user-images.githubusercontent.com/72151831/104055065-685e2a00-51ee-11eb-9379-f927065e2896.jpg)
![langue_italien](https://user-images.githubusercontent.com/72151831/104055066-685e2a00-51ee-11eb-930e-1af31e06d644.jpg)



## Responsive
L'application est responsive, aussi bien en format portrait qu'en paysage.

![responsive_accueil_1](https://user-images.githubusercontent.com/72151831/104055339-e6223580-51ee-11eb-8516-a90bcc2d973e.jpg)
![responsive_accueil_2](https://user-images.githubusercontent.com/72151831/104055340-e6bacc00-51ee-11eb-99c6-c24a36dfed8e.jpg)
![responsive_parametres](https://user-images.githubusercontent.com/72151831/104055341-e6bacc00-51ee-11eb-808c-7df7d6d38245.jpg)



