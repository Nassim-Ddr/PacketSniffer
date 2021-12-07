STRUCTURE DU CODE SOURCE ===================================== 
Les classes du code source sont séparées en 3 catégories : 

+++Classes principales : 

	Ces classes se trouvent dans le package « main ».

	Ce sont les classes qui contiennent les fonctions nécessaires au bon fonctionnement du programme. 

	Ces fonctions sont utilisées un peu partout dans les autres classes. 

	Les fonctions principales sont : 

	•	startApp() : Pour lancer l’application.
	•	loadFile() : Pour charger le fichier trace.
	•	cleanFile() : Pour détecter les erreurs et transformer le fichier trace en liste de trames (representé par une chaine de caractère)
	•	decodeTrace() : Commence le decodage de la trace.
	•	getByte(x, i) : Retourne le ieme octet de la trame x.
	•	getByteInRange(x, i, j) : Retourne les bytes de i à j de la trame x.
	•	getMissingByte(x, i) : retourne les bytes à partir de i jusqu’à la fin de la trame x.


+++Classes couches : 

	Ces classes suivent l’appellation : « Layeri » (avec i le numéro de la couche)

	Ces classes se trouvent dans des packages qui suivent la meme appelation. 

	Ces classes se chargent d’identifier le protocole utilisé dans la couche et de faire passer la trame au bon protocole.

	Ces classes contiennent la fonction :

	•	toLayer(x, params) : va décider de transmettre la trame x à un protocole selon les paramètres params.



+++Classes protocoles : 

Chaque protocole appartient au package qui correspond à sa couche. 

Chaque classe protocole va décoder la trame reçue, l’afficher, la sauvegarder dans un fichier et ensuite faire passer la trame à la couche supérieure.
 
Les fonctions principales des classes protocoles sont : 

	•	constructeur(x) : Va remplir les différents champs de classe protocole selon la trame x.
	•	display() : Va afficher les différents champs du protocoles
	•	writeResults() : va écrire le résultat dans le fichier en réponse.
	•	nextLayer() : va envoyer la trame à la couche suivante. 
