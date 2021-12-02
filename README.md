# Correcteur d'orthographe

Travail de Jerome Emery, Derek Piche, Mahmoud Labidi

## Utilisation

Cette application permet à un utilisateur d'entrer du text ou bien d'importer un fichier text depuis son ordinateur, et de corriger celui-ci. Pour ce faire, on importe un dictionnaire (fichier text), et ensuite on entre le text à corriger. Appuyer sur le bouton "Corriger" compare chaque mot entré aux mots du dicionnaire, et surligne un rouge les mots incorrects. Il est possible de "right click" sur un mot surligner en rouge pour faire apparaitre une liste de 5 mots similaires, et de remplacer le mot incorrect par un de ceux proposés. Finalement, l'utilisateur peut sauvegarder le document corrigé comme un fichier text. 

![](https://github.com/jerome8575/wordCorrectorIFT1025/blob/fd835c6e1ee6e7bfbf67ae9b055ce3ac7c4a91b2/Screenshot%202021-12-01%20203331.png)

## Fonctionnement du code

Lorsque le dictionnaire est importé, il est stocké dans un hashtable, contenu dans un objet de type dictionnaire. Cette classe contient des fonctions pour remplir son attribut hashtable, verifier si un mot appartient au dictionnaire, et trouver des mots similiares à un mot incorrect. Le modèle MVC, geré par les classes modèle, contrôle, et vue assure la gestion entre l'interface graphique et les fonctions accomplies par le dictionnaire. En itérant a travers chaque mot du textArea, on crée des objets MotIncorrect si le mot n'appartient pas au dictionnaire. Cet objet contient le mot, son index dans le text area ainsi qu'un ArrayList avec des reccomandations de mots. On utilise cet objet pour ensuite surligner les mauvais mots et reccomander des mots. 




