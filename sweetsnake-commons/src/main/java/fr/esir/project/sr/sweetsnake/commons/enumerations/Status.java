package fr.esir.project.sr.sweetsnake.commons.enumerations;


public enum Status
{

    AVAILABLE, PENDING, PLAYING;

    @Override
    public String toString() {
        switch (this) {
            case AVAILABLE:
                return "available";
            case PENDING:
                return "pending game session";
            case PLAYING:
                return "playing";
            default:
                return "unknown";
        }
    }

}
