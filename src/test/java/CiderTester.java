import enums.CiderStates;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CiderTester implements FsmModel {

    private CiderOperator systemUnderTest = new CiderOperator();

    private CiderStates model = CiderStates.HOME_PAGE;

    private boolean home = true, cat = false, details = false, search = false, bag = false, bagEdit = false;

    WebDriver driver;

    public Object getState() {
        return model;
    }

    public void reset(boolean r)
        {if (r) {
            systemUnderTest = new CiderOperator();
        }
        model = CiderStates.HOME_PAGE;


    }


    private boolean hasSelectedCategoryGaurd()
    {
        return getState().equals(CiderStates.CATEGORY_PAGE);
    }
    public @Action void hasSelectedCategory()
    {
        //Updating SUT
        systemUnderTest.goToCategory();
        model = CiderStates.HOME_PAGE;

        //Updating model
        home = false;
        cat = true;
        details = false;
        search = false;
        bag = false;
        bagEdit = false;

        assertEquals(cat, systemUnderTest.goToCategory());
    }

    private boolean hasSelectedProductGuard()
    {
        return getState().equals(CiderStates.PRODUCT_DETAILS_PAGE);
    }
    public @Action void hasSelectedProduct()
    {
        //Updating SUT
        systemUnderTest.goToProductDetails();

        //Updating model
        home = false;
        cat = false;
        details = true;
        search = false;
        bag = false;
        bagEdit = false;

        assertEquals(details, systemUnderTest.goToProductDetails());
    }

    private boolean hasEnteredSearchTermGuard()
    {
        return getState().equals(CiderStates.SEARCH_RESULTS_PAGE);
    }
    public @Action void hasEnteredSearchTerm()
    {
        //Updating SUT
        systemUnderTest.goToSearchResults();

        //Updating model
        home = false;
        cat = false;
        details = false;
        search = true;
        bag = false;
        bagEdit = false;

        assertEquals(search, systemUnderTest.goToSearchResults());
    }

    private boolean hasOpenedBagGuard()
    {
        return getState().equals(CiderStates.BAG_POPUP);
    }
    public @Action void hasOpenedBag()
    {
        //Updating SUT
        systemUnderTest.goToBag();

        //Updating model
        home = false;
        cat = false;
        details = false;
        search = false;
        bag = true;
        bagEdit = false;

        assertEquals(bag, systemUnderTest.goToBag());
    }

    private boolean isEditingBagGuard()
    {
        return getState().equals(CiderStates.BAG_EDIT_POPUP);
    }
    public @Action void isEditingBag()
    {
        //Updating SUT
        systemUnderTest.goToEditBag();

        //Updating model
        home = false;
        cat = false;
        details = false;
        search = false;
        bag = false;
        bagEdit = true;

        assertEquals(bagEdit, systemUnderTest.goToEditBag());
    }

    @Test
    public void CiderModelRunner() {
        final GreedyTester tester = new GreedyTester(new CiderTester()); //Creates a test generator that can generate random walks. A greedy random walk gives preference to transitions that have never been taken before. Once all transitions out of a state have been taken, it behaves the same as a random walk.
        tester.setRandom(new Random()); //Allows for a random path each time the model is run.
        tester.buildGraph(); //Builds a model of our FSM to ensure that the coverage metrics are correct.
        tester.addListener(new StopOnFailureListener()); //This listener forces the test class to stop running as soon as a failure is encountered in the model.
        tester.addListener("verbose"); //This gives you printed statements of the transitions being performed along with the source and destination states.
        tester.addCoverageMetric(new TransitionPairCoverage()); //Records the transition pair coverage i.e. the number of paired transitions traversed during the execution of the test.
        tester.addCoverageMetric(new StateCoverage()); //Records the state coverage i.e. the number of states which have been visited during the execution of the test.
        tester.addCoverageMetric(new ActionCoverage()); //Records the number of @Action methods which have ben executed during the execution of the test.
        tester.generate(500); //Generates 500 transitions
        tester.printCoverage(); //Prints the coverage metrics specified above.
    }
}
