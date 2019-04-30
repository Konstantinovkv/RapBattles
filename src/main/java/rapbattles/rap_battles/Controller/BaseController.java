package rapbattles.rap_battles.Controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import rapbattles.rap_battles.Util.ErrorMessage;
import rapbattles.rap_battles.Util.Exceptions.*;

import java.time.LocalDateTime;

public abstract class BaseController {

    static Logger log = Logger.getLogger(UserController.class.getName());

    @ExceptionHandler({MainException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage exceptionHandler(Exception e) {
        log.error(e.getMessage());
        return new ErrorMessage(e.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
    }
}
