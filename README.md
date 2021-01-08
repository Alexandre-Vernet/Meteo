# Projet_Dev_Mobile_Meteo
Projet de fin de module en Dev Mobile.
Ce projet a été réalisé en Java sous Android Studio 4.1.1 par Alexandre Vernet et Valentin Arpin

# 1er lancement
L'application va vérifier que l'appareil est bien connecté à Internet. Si c'est le cas, elle va demander la permission LOCALISATION. Si l'utilisateur accepte, l'application va déterminer la position de l'utilisateur et afficher la météo de la ville dans laquelle il se situe. L'application récupère le nom de la ville, la température, les températures minimum et maximum de la journée, le taux d'humidité, la vitesse du vent ainsi que l'heure de lever et de coucher du soleil. 
Si l'utilisateur refuse la permission localisation, l'application va automatiquement récupérer la météo de Paris et suggérer d'autoriser la localisation pour le bon fonctionnement de l'application. 

# Graphiques
Egalement, nous avons utilisé la librairie AndroidChart pour créer un graphique de prévision pour la semaine permettant de prévoir les températures de la semaine en cours.

# Menu
Un bouton est présent en bas à droite de l'écran pour afficher un menu. Ce menu possède 3 options : 
- Récupérer la météo de la ville dans laquelle l'appareil se situe
- Saisir une ville pour récupérer la météo de n'importe quelle ville de France
- Paramètres pour modifier les unités de températures (°C ou °F) ainsi que la vitesse du vent (km / h, mph, noeuds etc). Ce menu inclut également la version de l'application ainsi que le noms des développeurs.

# Responsive
L'application est responsive, aussi bien en format portrait qu'en paysage.

# Raccourcis
Des raccourcis sur l'icone de l'application sont présent pour démarrer directement le menu "Saisir une ville" ou "Paramètres".

# Widget
L'app possède son propre widget affichant le nom de la ville dans laquelle l'appareil a été localisé ainsi que la température actuelle.
Au clic du widget, l'application se lance.

# Langues
L'application possède plusieurs fichiers de traductions : 
- Français
- Espagnol
- Italien
- Allemand
L'application est capable de reconnaître la langue utilisée par l'appareil. Si ce dernier est configuré dans une des langues citées au dessus, elle traduira chaque texte dans la traduction appropriée, sinon, elle choisira l'anglais comme langue par défaut.
