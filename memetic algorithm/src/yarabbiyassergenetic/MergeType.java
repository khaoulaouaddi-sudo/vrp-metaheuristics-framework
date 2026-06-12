package yarabbiyassergenetic;

/* 
 * Décrit comment un chemin élémentaire doit être fusionné
 * avec l'autre pour obtenir le gain reporté par la classe
 * 'Saving' qui les contient. 
 */
public enum MergeType {
	HEAD_TO_TAIL, // passage direct du premier magasin du etour1 vers le dernier magasin du etour2
	HEAD_TO_HEAD, // passage direct du premier magasin du etour1 vers le premier magasin du etour2
	TAIL_TO_HEAD, // passage direct du dernier magasin du etour1 vers le premier magasin du etour2
	TAIL_TO_TAIL  // passage direct du dernier magasin du etour1 vers le dernier magasin du etour2
}
