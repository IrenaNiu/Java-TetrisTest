package tested;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BlockGridTest.class, TetrisBlockTest.class, TetrisGridTest.class })
public class AllTests {

}
