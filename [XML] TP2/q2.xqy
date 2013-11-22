<result> {
let $c := doc("produit.xml")/listeProduit/produit[Couleur="vert"]
	return count($c)
} </result>