import enums.CiderStates;
import nz.ac.waikato.modeljunit.FsmModel;

public class CiderTester implements FsmModel {

    private CiderOperator systemUnderTest = new CiderOperator();

    private CiderStates model = CiderStates.HOME_PAGE;

    public Object getState() {
        return model;
    }

    public void reset(boolean b) {

    }
}
