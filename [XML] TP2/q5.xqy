<result> {
	for $F in distinct-values(
		for
			$fournisseur in doc("fournisseur.xml")/listeFournisseur/fournisseur,
			$maFourniture in doc("maFourniture.xml")/listeFourniture/fourniture,
			$produit in doc("produit.xml")/listeProduit/produit
			where
				$maFourniture/F = $fournisseur/F and
				$maFourniture/P = $produit/P and
				$produit/Couleur = "vert"
			return
				$fournisseur/F)
	return <F>{$F}</F>
} </result>