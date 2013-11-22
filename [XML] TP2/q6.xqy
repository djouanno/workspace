<result> {
	for $P in distinct-values(
		for
			$fournisseur in doc("fournisseur.xml")/listeFournisseur/fournisseur,
			$maFourniture in doc("maFourniture.xml")/listeFourniture/fourniture,
			$produit in doc("produit.xml")/listeProduit/produit
			where
				$maFourniture/F = $fournisseur/F and
				$maFourniture/P = $produit/P and
				$fournisseur/Nom = "Barnibus" and
				$produit/Couleur = "vert"
			return
				$produit/Nom_p)
	return <produit>{$P}</produit>
} </result>