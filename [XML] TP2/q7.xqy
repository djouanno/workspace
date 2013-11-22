(: TODO distinct value :)
<result> {
for
	$fournisseur in doc("fournisseur.xml")/listeFournisseur/fournisseur,
	$maFourniture in doc("maFourniture.xml")/listeFourniture/fourniture
where
	$fournisseur/F = $maFourniture/F
return
	if (count($maFourniture[P="p2"]) >= 1 and count($maFourniture[P="p4"]) >= 1) then
		$fournisseur
	else
		()
} </result>