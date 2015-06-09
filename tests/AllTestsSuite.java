package trail;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    trail.input.InputParserTest.class,
    trail.filesystem.ReaderTest.class,
})
public class AllTestsSuite {}
