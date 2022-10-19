import org.jsmart.zerocode.core.domain.JsonTestCase;
import org.jsmart.zerocode.core.domain.TargetEnv;
import org.jsmart.zerocode.core.runner.ZeroCodeUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ZeroCodeUnitRunner.class)
@TargetEnv("host.properties")
public class TransferControllerIT {

    @Test
    @JsonTestCase("transfer.json")
    public void test_user_creation_endpoint() {
    }
}
