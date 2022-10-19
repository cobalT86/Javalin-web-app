import com.rev.interview.dao.AccountDaoImpl;
import com.rev.interview.exception.AccountNotFoundException;
import com.rev.interview.exception.AccountsAreTheSameException;
import com.rev.interview.exception.NotEnoughMoneyException;
import com.rev.interview.model.Account;
import com.rev.interview.service.TransferController;
import io.javalin.ExceptionHandler;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpStatus;

import javax.inject.Inject;
import java.math.BigDecimal;

@Slf4j
public class AppConfig {

    static final int DEFAULT_REST_PORT = 7000;
    private Javalin restApp;
    @Inject
    private static AccountDaoImpl accountDaoImpl;

    public static void main(String[] args) {

        accountDaoImpl.ACCOUNT_STORE.put(1L, new Account(1L, new BigDecimal(1000)));
        accountDaoImpl.ACCOUNT_STORE.put(2L, new Account(2L, new BigDecimal(5000)));


        new AppConfig().start(DEFAULT_REST_PORT);
    }

    private void start(int restPort) {
        final ExceptionHandler<RuntimeException> exceptionHandler = getExceptionExceptionHandler();

        restApp = Javalin.create().
                get("/", ctx -> ctx.result("Revolut Backend Test")).
                start(restPort);
        restApp.post("/findaccount/:id", TransferController::findAccountById);
        restApp.post("/transfer/:sum", TransferController::transferSumBetweenAccounts);
        restApp.post("/create", TransferController::createAccount);
        restApp.exception(IllegalArgumentException.class, exceptionHandler);
        restApp.exception(NotEnoughMoneyException.class, exceptionHandler);
        restApp.exception(AccountNotFoundException.class, exceptionHandler);
        restApp.exception(AccountsAreTheSameException.class, exceptionHandler);
    }

    private ExceptionHandler<RuntimeException> getExceptionExceptionHandler() {
        return (e, ctx) -> {
            log.error("Error", e);
            ctx.status(HttpStatus.BAD_REQUEST_400);
            ctx.result(e.toString());
        };
    }

}