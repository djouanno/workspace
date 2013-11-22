<result> {
let $c := doc("produit.xml")/listeProduit/produit[Couleur="vert" and Origine="Riec"]
	return count($c)
} </result>