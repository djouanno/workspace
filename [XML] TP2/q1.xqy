<result> {
for $p in doc("produit.xml")/listeProduit/produit[Couleur="vert"]
	return <Nom_P>{$p/Nom_p/text()}</Nom_P>
} </result>