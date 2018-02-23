package pons.carretero.myvocabulary.Objects;

/**
 * Created by carre on 04/12/2017.
 */

public class IrregularVerb {
    private String verb;
    private String pastSimple;
    private String pastParticiple;
    private String esp;

    public IrregularVerb (String v, String ps, String pp, String es){
        verb = v;
        pastSimple = ps;
        pastParticiple = pp;
        esp = es;
    }

    public String getVerb() {
        return verb;
    }

    public String getEsp() {
        return esp;
    }

    public String getPastSimple() {
        return pastSimple;
    }

    public String getPastParticiple() {
        return pastParticiple;
    }
}
