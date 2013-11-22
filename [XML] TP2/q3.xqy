(: fonction produitParCouleur :)
declare namespace local =
"http://www.irisa.fr/esir/test";
declare function local:produitParCouleur($c) {
	for $p in doc("produit.xml")
	/listeProduit/produit[Couleur=$c]
		return $p
};

<result> {
	local:produitParCouleur('vert')/Nom_p
} </result>