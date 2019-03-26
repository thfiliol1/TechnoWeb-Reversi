package fr.isima.business;

/**
 * Modelise les diff�rentes directions possibles.
 * 
 * 
 * @author Benjamin Kuchcik
 *
 */
public enum NavigationDirection {
    //
    FIRST("Premiere citation"),
    //
    NEXT("Citation suivante"),
    //
    PREVIOUS("Citation precedente"),
    //
    LAST("Derniere citation");

    private final String label;

    NavigationDirection(String label) {
        this.label = label;
    }

    public String getTargetLabel() {
        return label;
    }

    public String getId() {
        return name().toLowerCase();
    }
}
